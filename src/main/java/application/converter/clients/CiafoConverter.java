package application.converter.clients;

import application.converter.ItemsConverter;
import application.entity.clients.CiafoItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public final class CiafoConverter extends ItemsConverter {

    public List<CiafoItem> filterItemsToInsert(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.isNull(item.getId()))
                .filter(item -> !item.getDelete())
                .toList();
    }

    public List<CiafoItem> filterItemsToUpdate(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
                .filter(item -> !item.getDelete())
                .toList();
    }

    public List<Long> filterIdsToDelete(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
                .filter(CiafoItem::getDelete)
                .map(CiafoItem::getId)
                .toList();
    }

    public List<CiafoItem> processItems(String category, Map<String, String> params, Map<String, MultipartFile> files) {
        return getIterators(params)
                .stream()
                .map(iterator -> {
                    CiafoItem item = new CiafoItem();
                    item.setId(getValue(params, iterator, "id")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .map(Long::valueOf)
                            .orElse(null));
                    item.setCategory(category);
                    item.setDelete(getValue(params, iterator, "delete")
                            .filter(delete -> !StringUtils.isEmptyOrWhitespace(delete))
                            .map("on"::equals)
                            .orElse(false));
                    item.setDescription(getValue(params, iterator, "description")
                            .filter(description -> !StringUtils.isEmptyOrWhitespace(description))
                            .orElse(null));
                    item.setImage1(getImage(files, iterator, "image1"));
                    item.setImage2(getImage(files, iterator, "image2"));
                    item.setImage3(getImage(files, iterator, "image3"));
                    item.setImage4(getImage(files, iterator, "image4"));
                    item.setThumbnail1(getThumbnail(files, iterator, "image1"));
                    item.setThumbnail2(getThumbnail(files, iterator, "image2"));
                    item.setThumbnail3(getThumbnail(files, iterator, "image3"));
                    item.setThumbnail4(getThumbnail(files, iterator, "image4"));
                    return item;
                })
                .toList();
    }
}