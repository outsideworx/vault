package application.converter.client;

import application.converter.ItemsConverter;
import application.entity.client.CiafoItem;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class CiafoConverter extends ItemsConverter {

    public List<CiafoItem> filterItemsToInsert(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.isNull(item.getId()))
                .toList();
    }

    public List<CiafoItem> filterItemsToUpdate(List<CiafoItem> items) {
        return items
                .stream()
                .filter(item -> Objects.nonNull(item.getId()))
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
                    item.setDescription(getValue(params, iterator, "description")
                            .filter(id -> !StringUtils.isEmptyOrWhitespace(id))
                            .orElse(null));
                    item.setImage1(getImageBytes(files, iterator, "image1"));
                    item.setImage2(getImageBytes(files, iterator, "image2"));
                    item.setImage3(getImageBytes(files, iterator, "image3"));
                    item.setImage4(getImageBytes(files, iterator, "image4"));
                    return item;
                })
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
                .map(this::reduceQuality)
                .orElse(null);
    }

    private byte[] reduceQuality(byte[] image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(image));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            Thumbnails.of(originalImage)
                    .size((int) (width * 0.66), (int) (height * 0.66))
                    .outputQuality(0.33)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Image compression failed.", e);
        }
        return outputStream.toByteArray();
    }
}