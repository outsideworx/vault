package application.converter.client;

import application.converter.ItemsConverter;
import application.entity.client.CiafoItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class CiafoConverter extends ItemsConverter {

    public List<CiafoItem> processRequestParams(String category, Map<String, String> params, Map<String, MultipartFile> files) {
        return getIterators(params)
                .stream()
                .map(iterator -> {
                    CiafoItem item = new CiafoItem();
                    item.setId(getValue(params, iterator, "id")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .map(Long::valueOf)
                            .orElse(null));
                    item.setCategory(category);
                    item.setDescription(getValue(params, iterator, "description")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .orElse(null));
                    byte[] image1 = getImageBytes(files, iterator, "image1");
                    if (Objects.nonNull(image1)) {
                        item.setImage1(image1);
                    }
                    byte[] image2 = getImageBytes(files, iterator, "image2");
                    if (Objects.nonNull(image2)) {
                        item.setImage2(image2);
                    }
                    byte[] image3 = getImageBytes(files, iterator, "image3");
                    if (Objects.nonNull(image3)) {
                        item.setImage3(image3);
                    }
                    byte[] image4 = getImageBytes(files, iterator, "image4");
                    if (Objects.nonNull(image4)) {
                        item.setImage4(image4);
                    }
                    return item;
                })
                .toList();
    }

    public List<Long> getIdsToDelete(Map<String, String> params) {
        return getIterators(params)
                .stream()
                .filter(iterator -> {
                    Optional<String> delete = getValue(params, iterator, "delete");
                    return delete.map(Boolean::valueOf).orElse(false);
                })
                .map(iterator -> {
                    return getValue(params, iterator, "id")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .map(Long::valueOf)
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private byte[] getImageBytes(Map<String, MultipartFile> files, Integer iterator, String field) {
        return getValue(files, iterator, field)
                .map(multipartFile -> {
                    try {
                        return multipartFile.getBytes();
                    } catch (IOException e) {
                        throw new IllegalStateException("Image processing failed.", e);
                    }
                })
                .filter(bytes -> bytes.length > 0)
                .orElse(null);
    }
}
