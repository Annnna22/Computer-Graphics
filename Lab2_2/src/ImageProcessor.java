
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor extends JFrame {
    private BufferedImage originalImage;
    private JLabel originalImageLabel;
    private JLabel processedImageLabel;
    private JLabel processedImageLabel2;

    public ImageProcessor() {
        setTitle("Приложение для обработки изображений");
        setSize(1503, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton loadButton = new JButton("Загрузить");
        loadButton.setBounds(5, 10, 100, 20);
        JButton contrastButton = new JButton("Линейное контрастирование");
        contrastButton.setBounds(105, 10, 220, 20);
        JButton thresholdButton2 = new JButton("Пороговая обработка (Оцу)");
        thresholdButton2.setBounds(890, 10, 210, 20);
        JButton thresholdButton3 = new JButton("Глобальная с гистограммой");
        thresholdButton3.setBounds(1100, 10, 230, 20);

        JLabel orImg = new JLabel("Оригинальное изображение:");
        orImg.setBounds(0, 25, 200, 30);
        JLabel newImg = new JLabel("Обработанное линейным или Отсу:");
        newImg.setBounds(500, 25, 250, 30);
        JLabel newImg2 = new JLabel("Глобальная с гистограммой:");
        newImg2.setBounds(1000, 25, 200, 30);

        originalImageLabel = new JLabel();
        originalImageLabel.setBounds(0, 50, 500, 500);
        processedImageLabel = new JLabel();
        processedImageLabel.setBounds(500, 50, 500, 500);
        processedImageLabel2 = new JLabel();
        processedImageLabel2.setBounds(1000, 50, 500, 500);

        JButton addConstantButton = new JButton("Сложение");
        addConstantButton.setBounds(325, 10, 100, 20);

        JButton negativeButton = new JButton("Негатив");
        negativeButton.setBounds(530, 10, 100, 20);

        JButton gammaButton = new JButton("Гамма-коррекция");
        gammaButton.setBounds(630, 10, 150, 20);

        JButton logButton = new JButton("Логарифм");
        logButton.setBounds(780, 10, 110, 20);
        JButton multiplyButton = new JButton("Умножение");
        multiplyButton.setBounds(425, 10, 105, 20);

        add(multiplyButton);

        multiplyButton.addActionListener(e -> {
            if (originalImage != null) {
                float constant = Float.parseFloat(JOptionPane.showInputDialog("Введите коэффициент контрастности (например, 1.2):"));
                BufferedImage result = multiplyByConstant(originalImage, constant);
                processedImageLabel.setIcon(new ImageIcon(result));
            }
        });


        add(addConstantButton);
        add(negativeButton);
        add(gammaButton);
        add(logButton);

        addConstantButton.addActionListener(e -> {
            if (originalImage != null) {
                int constant = Integer.parseInt(JOptionPane.showInputDialog("Введите константу:"));
                BufferedImage result = addConstant(originalImage, constant);
                processedImageLabel.setIcon(new ImageIcon(result));
            }
        });

        negativeButton.addActionListener(e -> {
            if (originalImage != null) {
                BufferedImage result = applyNegative(originalImage);
                processedImageLabel.setIcon(new ImageIcon(result));
            }
        });

        gammaButton.addActionListener(e -> {
            if (originalImage != null) {
                double gamma = Double.parseDouble(JOptionPane.showInputDialog("Введите гамма-значение:"));
                BufferedImage result = applyGammaCorrection(originalImage, gamma);
                processedImageLabel.setIcon(new ImageIcon(result));
            }
        });

        logButton.addActionListener(e -> {
            if (originalImage != null) {
                BufferedImage result = applyLogarithmicTransformation(originalImage);
                processedImageLabel.setIcon(new ImageIcon(result));
            }
        });

        loadButton.addActionListener(e -> loadImage());
        contrastButton.addActionListener(e -> {
            if (originalImage != null) {
                BufferedImage contrastedImage = applyLinearContrast(originalImage);
                processedImageLabel.setIcon(new ImageIcon(contrastedImage));
            }
        });


        thresholdButton2.addActionListener(e -> {
            if (originalImage != null) {
                BufferedImage otsuThresholdedImage = applyOtsuThreshold(originalImage);
                processedImageLabel.setIcon(new ImageIcon(otsuThresholdedImage));
            }
        });

        thresholdButton3.addActionListener(e -> {
            if (originalImage != null) {
                BufferedImage result = applyGlobalThresholding(originalImage);
                processedImageLabel2.setIcon(new ImageIcon(result));
            }
        });

        add(loadButton);
        add(contrastButton);
        add(thresholdButton2);
        add(thresholdButton3);
        add(orImg);
        add(newImg);
        add(newImg2);
        add(originalImageLabel);
        add(processedImageLabel);
        add(processedImageLabel2);
    }


    private BufferedImage addConstant(BufferedImage image, int constant) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = ((rgb >> 16) & 0xff) + constant;
                int g = ((rgb >> 8) & 0xff) + constant;
                int b = (rgb & 0xff) + constant;

                r = Math.max(0, Math.min(r, 255));
                g = Math.max(0, Math.min(g, 255));
                b = Math.max(0, Math.min(b, 255));

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        return newImage;
    }

    private BufferedImage applyNegative(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = 255 - ((rgb >> 16) & 0xff);
                int g = 255 - ((rgb >> 8) & 0xff);
                int b = 255 - (rgb & 0xff);

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        return newImage;
    }

    private BufferedImage multiplyByConstant(BufferedImage image, float constant) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (int)(((rgb >> 16) & 0xff) * constant);
                int g = (int)(((rgb >> 8) & 0xff) * constant);
                int b = (int)((rgb & 0xff) * constant);

                r = Math.max(0, Math.min(r, 255));
                g = Math.max(0, Math.min(g, 255));
                b = Math.max(0, Math.min(b, 255));

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        return newImage;
    }

    private BufferedImage applyGammaCorrection(BufferedImage image, double gamma) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (int)(255 * Math.pow(((rgb >> 16) & 0xff) / 255.0, gamma));
                int g = (int)(255 * Math.pow(((rgb >> 8) & 0xff) / 255.0, gamma));
                int b = (int)(255 * Math.pow((rgb & 0xff) / 255.0, gamma));

                r = Math.max(0, Math.min(r, 255));
                g = Math.max(0, Math.min(g, 255));
                b = Math.max(0, Math.min(b, 255));

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        return newImage;
    }

    private BufferedImage applyLogarithmicTransformation(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (int)(255 * (Math.log(1 + ((rgb >> 16) & 0xff)) / Math.log(256)));
                int g = (int)(255 * (Math.log(1 + ((rgb >> 8) & 0xff)) / Math.log(256)));
                int b = (int)(255 * (Math.log(1 + (rgb & 0xff)) / Math.log(256)));

                int newRgb = (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newRgb);
            }
        }
        return newImage;
    }


    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(selectedFile);
                originalImageLabel.setIcon(new ImageIcon(originalImage));

                processedImageLabel.setIcon(null);
                processedImageLabel2.setIcon(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*private BufferedImage applyLinearContrast(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] histogram = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                int intensity = (r + g + b) / 3;
                histogram[intensity]++;
            }
        }

        int[] cumulativeHistogram = new int[256];
        cumulativeHistogram[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
        }

        int totalPixels = width * height;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                int intensity = (r + g + b) / 3;

                int newIntensity = (cumulativeHistogram[intensity] * 255) / totalPixels;
                newIntensity = Math.max(0, Math.min(newIntensity, 255));

                int newRgb = (newIntensity << 16) | (newIntensity << 8) | newIntensity;
                newImage.setRGB(x, y, newRgb);
            }
        }

        return newImage;
    }*/
    private BufferedImage applyLinearContrast(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int minIntensity = 255;
        int maxIntensity = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                int intensity = (r + g + b) / 3;

                if (intensity < minIntensity) minIntensity = intensity;
                if (intensity > maxIntensity) maxIntensity = intensity;
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                int intensity = (r + g + b) / 3;

                int newIntensity = (int)(((float)(intensity - minIntensity) / (maxIntensity - minIntensity)) * 255);

                newIntensity = Math.max(0, Math.min(newIntensity, 255)); // Ограничение диапазона

                int newRgb = (newIntensity << 16) | (newIntensity << 8) | newIntensity;
                newImage.setRGB(x, y, newRgb);
            }
        }

        return newImage;
    }


    private BufferedImage applyOtsuThreshold(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] histogram = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int intensity = (rgb >> 16) & 0xff;
                histogram[intensity]++;
            }
        }

        int totalPixels = width * height;
        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        float sumB = 0;
        int weightB = 0;
        int weightF;
        float maxVariance = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            weightB += histogram[t];
            if (weightB == 0) continue;

            weightF = totalPixels - weightB;
            if (weightF == 0) break;

            sumB += (float) (t * histogram[t]);
            float meanB = sumB / weightB;
            float meanF = (sum - sumB) / weightF;

            float variance = weightB * weightF * (meanB - meanF) * (meanB - meanF);
            if (variance > maxVariance) {
                maxVariance = variance;
                threshold = t;
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int intensity = (rgb >> 16) & 0xff;
                int newIntensity = intensity >= threshold ? 255 : 0;
                int newRgb = (newIntensity << 16) | (newIntensity << 8) | newIntensity;
                binaryImage.setRGB(x, y, newRgb);
            }
        }

        return binaryImage;
    }

    private BufferedImage applyGlobalThresholding(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixels = width * height;
        int[] histogram = new int[256];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int intensity = (rgb >> 16) & 0xff;
                histogram[intensity]++;
            }
        }

        int threshold = 128; // Начальная оценка
        int newThreshold;
        double epsilon = 1;

        do {
            newThreshold = threshold;

            double sum1 = 0, sum2 = 0;
            int count1 = 0, count2 = 0;

            for (int i = 0; i < 256; i++) {
                if (i <= threshold) {
                    sum1 += i * histogram[i];
                    count1 += histogram[i];
                } else {
                    sum2 += i * histogram[i];
                    count2 += histogram[i];
                }
            }

            double mean1 = count1 == 0 ? 0 : sum1 / count1;
            double mean2 = count2 == 0 ? 0 : sum2 / count2;

            threshold = (int) ((mean1 + mean2) / 2);

        } while (Math.abs(threshold - newThreshold) > epsilon);

        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int intensity = (rgb >> 16) & 0xff;
                int newIntensity = intensity >= threshold ? 255 : 0;
                int newRgb = (newIntensity << 16) | (newIntensity << 8) | newIntensity;
                binaryImage.setRGB(x, y, newRgb);
            }
        }

        return binaryImage;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageProcessor processor = new ImageProcessor();
            processor.setVisible(true);
        });
    }
}