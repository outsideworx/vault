package application.converter.clients;

import application.converter.ItemsConverter;
import application.entity.clients.ciafo.CiafoItem;
import application.entity.clients.peeps.PeepsItem;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public final class PeepsConverter extends ItemsConverter {
    public List<Long> filterIdsToDelete(List<PeepsItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
                .filter(PeepsItem::getDelete)
                .map(PeepsItem::getId)
                .toList();
    }

    public List<PeepsItem> processItems(Map<String, String> params) {
        return getIterators(params)
                .stream()
                .map(iterator -> {
                    PeepsItem item = new PeepsItem();
                    item.setId(getValue(params, iterator, "id")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .map(Long::valueOf)
                            .orElse(null));
                    item.setDelete(getValue(params, iterator, "delete")
                            .filter(delete -> !StringUtils.isEmptyOrWhitespace(delete))
                            .map("on"::equals)
                            .orElse(false));
                    item.setTitle(getValue(params, iterator, "title")
                            .filter(title -> !StringUtils.isEmptyOrWhitespace(title))
                            .orElse(null));
                    item.setLink(getValue(params, iterator, "link")
                            .filter(link -> !StringUtils.isEmptyOrWhitespace(link))
                            .orElse(null));
                    return item;
                })
                .toList();
    }
}