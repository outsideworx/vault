package application.converter;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ItemsConverter {
    private final Pattern iteratorPattern = Pattern.compile("(?<=items\\[)[0-9]+(?=]\\.)");

    protected <T> Optional<T> getValue(Map<String, T> params, int iterator, String field) {
        return params
                .entrySet()
                .stream()
                .filter(entry -> String.format("items[%d].%s", iterator, field).equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .findAny();
    }

    protected List<Integer> getIterators(Map<String, String> params) {
        return params
                .keySet()
                .stream()
                .map(iteratorPattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(0))
                .map(Integer::valueOf)
                .distinct()
                .toList();
    }

    protected byte[] getImageBytes(Map<String, MultipartFile> files, Integer iterator, String field) {
        return getValue(files, iterator, field)
                .map(multipartFile -> {
                    try {
                        return multipartFile.getBytes();
                    } catch (IOException e) {
                        throw new IllegalStateException("Image processing failed.", e);
                    }
                })
                .filter(bytes -> bytes.length > 0)
                .map(bytes -> reduceQuality(bytes, 0.33, 0.33, 0.5))
                .orElse(null);
    }

    public static byte[] reduceQuality(byte[] bytes, double widthQuality, double heightQuality, double outputQuality) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
            int width = image.getWidth();
            int height = image.getHeight();
            Thumbnails.of(image)
                    .size((int) (width * widthQuality), (int) (height * heightQuality))
                    .outputQuality(outputQuality)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Image compression failed.", e);
        }
        return outputStream.toByteArray();
    }
}
