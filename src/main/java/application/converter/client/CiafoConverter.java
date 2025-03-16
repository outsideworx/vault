package application.converter.client;

import application.converter.ItemsConverter;
import application.entity.client.CiafoItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CiafoConverter extends ItemsConverter {

    public List<CiafoItem> filterItemsToInsert(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.isNull(item.getId()))
                .filter(item -> !item.getSold())
                .toList();
    }

    public List<CiafoItem> filterItemsToUpdate(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
                .filter(item -> !item.getSold())
                .toList();
    }

    public List<Long> filterIdsToDelete(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
                .filter(CiafoItem::getSold)
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
                    item.setSold(getValue(params, iterator, "sold")
                            .filter(sold -> !StringUtils.isEmptyOrWhitespace(sold))
                            .map("on"::equals)
                            .orElse(false));
                    item.setDescription(getValue(params, iterator, "description")
                            .filter(description -> !StringUtils.isEmptyOrWhitespace(description))
                            .orElse(null));
                    item.setImage1(getImageBytes(files, iterator, "image1"));
                    item.setImage2(getImageBytes(files, iterator, "image2"));
                    item.setImage3(getImageBytes(files, iterator, "image3"));
                    item.setImage4(getImageBytes(files, iterator, "image4"));
                    return item;
                })
                .toList();
    }
}