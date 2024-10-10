import javax.swing.*;
import java.awt.*;

public class ColorConverterApp2 extends JFrame {
    private JTextField rField, gField, bField; // RGB
    private JTextField cField, mField, yField, kField; // CMYK
    private JTextField hField, sField, vField; // HSV
    private JPanel colorDisplay;
    private JSlider rSlider, gSlider, bSlider; // Sliders for RGB
    private JSlider cSlider, mSlider, ySlider, kSlider; // Sliders for CMYK
    private JSlider hSlider, sSlider, vSlider; // Sliders for HSV
    private int countR = 0;
    private int countC = 0;
    private int countH = 0;

    public ColorConverterApp2() {
        setTitle("Color Converter");
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Устанавливаем null layout для абсолютного позиционирования


        // Добавляем кнопку для выбора цвета через палитру
        JButton chooseColorButton = new JButton("Choose Color");
        chooseColorButton.setBounds(320, 20, 150, 25);
        chooseColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Choose a Color", colorDisplay.getBackground());
            if (selectedColor != null) {
                rSlider.setValue(selectedColor.getRed());
                gSlider.setValue(selectedColor.getGreen());
                bSlider.setValue(selectedColor.getBlue());
                updateFromRGB(++countR);
                --countR;
            }
        });
        add(chooseColorButton);

        // Панель для RGB
        JLabel rLabel = new JLabel("R");
        rLabel.setBounds(20, 20, 20, 25);
        add(rLabel);
        rField = new JTextField("0");
        rField.setBounds(40, 20, 50, 25);
        add(rField);

        rSlider = new JSlider(0, 255);
        rSlider.setBounds(100, 20, 200, 25);
        rSlider.setValue(0);
        rSlider.addChangeListener(e -> {
            rField.setText(String.valueOf(rSlider.getValue()));
            updateFromRGB(++countR);
            --countR;
        });
        add(rSlider);

        JLabel gLabel = new JLabel("G");
        gLabel.setBounds(20, 60, 20, 25);
        add(gLabel);
        gField = new JTextField("0");
        gField.setBounds(40, 60, 50, 25);
        add(gField);

        gSlider = new JSlider(0, 255);
        gSlider.setBounds(100, 60, 200, 25);
        gSlider.setValue(0);
        gSlider.addChangeListener(e -> {
            gField.setText(String.valueOf(gSlider.getValue()));
            updateFromRGB(++countR);
            --countR;
        });
        add(gSlider);

        JLabel bLabel = new JLabel("B");
        bLabel.setBounds(20, 100, 20, 25);
        add(bLabel);
        bField = new JTextField("0");
        bField.setBounds(40, 100, 50, 25);
        add(bField);

        bSlider = new JSlider(0, 255);
        bSlider.setBounds(100, 100, 200, 25);
        bSlider.setValue(0);
        bSlider.addChangeListener(e -> {
            bField.setText(String.valueOf(bSlider.getValue()));
            updateFromRGB(++countR);
            --countR;
        });
        add(bSlider);

        JButton rgbButton = new JButton("Set RGB");
        rgbButton.setBounds(320, 60, 100, 25);
        rgbButton.addActionListener(e -> {
            rSlider.setValue(Integer.parseInt(rField.getText()));
            gSlider.setValue(Integer.parseInt(gField.getText()));
            bSlider.setValue(Integer.parseInt(bField.getText()));
            //updateFromRGB();
        });
        add(rgbButton);


        // Панель для CMYK
        JLabel cLabel = new JLabel("C");
        cLabel.setBounds(20, 140, 20, 25);
        add(cLabel);
        cField = new JTextField("0");
        cField.setBounds(40, 140, 50, 25);
        add(cField);

        cSlider = new JSlider(0, 100);
        cSlider.setBounds(100, 140, 200, 25);
        cSlider.setValue(0);
        cSlider.addChangeListener(e -> {
            cField.setText(String.valueOf(cSlider.getValue()));
            updateFromCMYK(++countC);
            --countC;
        });
        add(cSlider);

        JLabel mLabel = new JLabel("M");
        mLabel.setBounds(20, 180, 20, 25);
        add(mLabel);
        mField = new JTextField("0");
        mField.setBounds(40, 180, 50, 25);
        add(mField);

        mSlider = new JSlider(0, 100);
        mSlider.setBounds(100, 180, 200, 25);
        mSlider.setValue(0);
        mSlider.addChangeListener(e -> {
            mField.setText(String.valueOf(mSlider.getValue()));
            updateFromCMYK(++countC);
            --countC;
        });
        add(mSlider);

        JLabel yLabel = new JLabel("Y");
        yLabel.setBounds(20, 220, 20, 25);
        add(yLabel);
        yField = new JTextField("0");
        yField.setBounds(40, 220, 50, 25);
        add(yField);

        ySlider = new JSlider(0, 100);
        ySlider.setBounds(100, 220, 200, 25);
        ySlider.setValue(0);
        ySlider.addChangeListener(e -> {
            yField.setText(String.valueOf(ySlider.getValue()));
            updateFromCMYK(++countC);
            --countC;
        });
        add(ySlider);

        JLabel kLabel = new JLabel("K");
        kLabel.setBounds(20, 260, 20, 25);
        add(kLabel);
        kField = new JTextField("100");
        kField.setBounds(40, 260, 50, 25);
        add(kField);

        kSlider = new JSlider(0, 100);
        kSlider.setBounds(100, 260, 200, 25);
        kSlider.setValue(100);
        kSlider.addChangeListener(e -> {
            kField.setText(String.valueOf(kSlider.getValue()));
            updateFromCMYK(++countC);
            --countC;
        });
        add(kSlider);

        JButton cmykButton = new JButton("Set CMYK");
        cmykButton.setBounds(320, 140, 100, 25);
        cmykButton.addActionListener(e -> {
            cSlider.setValue(Integer.parseInt(cField.getText()));
            mSlider.setValue(Integer.parseInt(mField.getText()));
            ySlider.setValue(Integer.parseInt(yField.getText()));
            kSlider.setValue(Integer.parseInt(kField.getText()));
            //updateFromCMYK();
        });
        add(cmykButton);

        // Панель для HSV
        JLabel hLabel = new JLabel("H");
        hLabel.setBounds(20, 300, 20, 25);
        add(hLabel);
        hField = new JTextField("0");
        hField.setBounds(40, 300, 50, 25);
        add(hField);

        hSlider = new JSlider(0, 360);
        hSlider.setBounds(100, 300, 200, 25);
        hSlider.setValue(0);
        hSlider.addChangeListener(e -> {
            hField.setText(String.valueOf(hSlider.getValue()));
            updateFromHSV(++countH);
            --countH;
        });
        add(hSlider);

        JLabel sLabel = new JLabel("S");
        sLabel.setBounds(20, 340, 20, 25);
        add(sLabel);
        sField = new JTextField("0");
        sField.setBounds(40, 340, 50, 25);
        add(sField);

        sSlider = new JSlider(0, 100);
        sSlider.setBounds(100, 340, 200, 25);
        sSlider.setValue(0);
        sSlider.addChangeListener(e -> {
            sField.setText(String.valueOf(sSlider.getValue()));
            updateFromHSV(++countH);
            --countH;
        });
        add(sSlider);

        JLabel vLabel = new JLabel("V");
        vLabel.setBounds(20, 380, 20, 25);
        add(vLabel);
        vField = new JTextField("0");
        vField.setBounds(40, 380, 50, 25);
        add(vField);

        vSlider = new JSlider(0, 100);
        vSlider.setBounds(100, 380, 200, 25);
        vSlider.setValue(0);
        vSlider.addChangeListener(e -> {
            vField.setText(String.valueOf(vSlider.getValue()));
            updateFromHSV(++countH);
            --countH;
        });
        add(vSlider);

        JButton hsvButton = new JButton("Set HSV");
        hsvButton.setBounds(320, 300, 100, 25);
        hsvButton.addActionListener(e -> {
            hSlider.setValue(Integer.parseInt(hField.getText()));
            sSlider.setValue(Integer.parseInt(sField.getText()));
            vSlider.setValue(Integer.parseInt(vField.getText()));
            //updateFromHSV();
        });
        add(hsvButton);

        // Панель для отображения цвета
        colorDisplay = new JPanel();
        colorDisplay.setBackground(Color.BLACK);
        colorDisplay.setBounds(20, 420, 440, 100);
        add(colorDisplay);

        setVisible(true);
    }

    private void updateFromRGB(int coun) {
        try {
            if(coun<2) {
                int r = rSlider.getValue();
                int g = gSlider.getValue();
                int b = bSlider.getValue();

                // Update CMYK
                int[] cmyk = rgbToCmyk(r, g, b);
                cField.setText(String.valueOf(cmyk[0]));
                cSlider.setValue(cmyk[0]);
                mField.setText(String.valueOf(cmyk[1]));
                mSlider.setValue(cmyk[1]);
                yField.setText(String.valueOf(cmyk[2]));
                ySlider.setValue(cmyk[2]);
                kField.setText(String.valueOf(cmyk[3]));
                kSlider.setValue(cmyk[3]);

                // Update HSV
                int[] hsv = rgbToHsv(r, g, b);
                hField.setText(String.valueOf(hsv[0]));
                hSlider.setValue(hsv[0]);
                sField.setText(String.valueOf(hsv[1]));
                sSlider.setValue(hsv[1]);
                vField.setText(String.valueOf(hsv[2]));
                vSlider.setValue(hsv[2]);

                updateColorDisplay(r, g, b);
            }
        } catch (NumberFormatException e) {
            showError();
        }
    }

    private void updateFromCMYK(int coun) {
        try { if(coun<2) {
            float c = Float.parseFloat(cField.getText()) / 100;
            float m = Float.parseFloat(mField.getText()) / 100;
            float y = Float.parseFloat(yField.getText()) / 100;
            float k = Float.parseFloat(kField.getText()) / 100;
            // float c = cSlider.getValue() / 100;
            // float m = mSlider.getValue() / 100;
            // float y = ySlider.getValue() / 100;
            // float k = kSlider.getValue() / 100;

            // Update RGB
            int[] rgb = cmykToRgb(c, m, y, k);
            rSlider.setValue(rgb[0]);
            gSlider.setValue(rgb[1]);
            bSlider.setValue(rgb[2]);

            // Update HSV
            int[] hsv = rgbToHsv(rgb[0], rgb[1], rgb[2]);
            hField.setText(String.valueOf(hsv[0]));
            sField.setText(String.valueOf(hsv[1]));
            vField.setText(String.valueOf(hsv[2]));

            updateColorDisplay(rgb[0], rgb[1], rgb[2]);
        }
        } catch (NumberFormatException e) {
            showError();
        }
    }

    private void updateFromHSV(int coun) {
        try { if(coun<2) {
            float h = Float.parseFloat(hField.getText());
            float s = Float.parseFloat(sField.getText()) / 100;
            float v = Float.parseFloat(vField.getText()) / 100;

            // Update RGB
            int[] rgb = hsvToRgb(h, s, v);
            rSlider.setValue(rgb[0]);
            gSlider.setValue(rgb[1]);
            bSlider.setValue(rgb[2]);

            // Update CMYK
            int[] cmyk = rgbToCmyk(rgb[0], rgb[1], rgb[2]);
            cField.setText(String.valueOf(cmyk[0]));
            mField.setText(String.valueOf(cmyk[1]));
            yField.setText(String.valueOf(cmyk[2]));
            kField.setText(String.valueOf(cmyk[3]));

            updateColorDisplay(rgb[0], rgb[1], rgb[2]);
        }
        } catch (NumberFormatException e) {
            showError();
        }
    }

    private void updateColorDisplay(int r, int g, int b) {
        colorDisplay.setBackground(new Color(r, g, b));  // Обновление цвета панели
    }

    private int[] rgbToCmyk(int r, int g, int b) {
        float c = 1 - (r / 255f);
        float m = 1 - (g / 255f);
        float y = 1 - (b / 255f);
        float k = Math.min(c, Math.min(m, y));

        if (k < 1) {
            c = (c - k) / (1 - k);
            m = (m - k) / (1 - k);
            y = (y - k) / (1 - k);
        } else {
            c = m = y = 0;
        }
        return new int[]{(int)(c*100), (int)(m*100), (int)(y*100), (int)(k*100)};
    }

    private int[] cmykToRgb(float c, float m, float y, float k) {
        int r = (int) ((1 - c) * (1 - k) * 255);
        int g = (int) ((1 - m) * (1 - k) * 255);
        int b = (int) ((1 - y) * (1 - k) * 255);
        return new int[]{r, g, b};
    }

    private int[] rgbToHsv(int r, int g, int b) {
        float rN = r / 255f;
        float gN = g / 255f;
        float bN = b / 255f;

        float max = Math.max(rN, Math.max(gN, bN));
        float min = Math.min(rN, Math.min(gN, bN));
        float h, s, v = max;

        float d = max - min;
        s = max == 0 ? 0 : d / max;

        if (max == min) {
            h = 0; // achromatic
        } else {
            if (max == rN) {
                h = (gN - bN) / d + (gN < bN ? 6 : 0);
            } else if (max == gN) {
                h = (bN - rN) / d + 2;
            } else {
                h = (rN - gN) / d + 4;
            }
            h /= 6;
        }
        return new int[]{(int)(h * 360), (int)(s*100), (int)(v*100)};
    }

    private int[] hsvToRgb(float h, float s, float v) {
        int r, g, b;
        int i = (int) Math.floor(h / 60) % 6;
        float f = (float) (h / 60 - Math.floor(h / 60));
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);
        v *= 255;
        p *= 255;
        q *= 255;
        t *= 255;

        switch (i) {
            case 0 -> {
                r = (int) v;
                g = (int) t;
                b = (int) p;
            }
            case 1 -> {
                r = (int) q;
                g = (int) v;
                b = (int) p;
            }
            case 2 -> {
                r = (int) p;
                g = (int) v;
                b = (int) t;
            }
            case 3 -> {
                r = (int) p;
                g = (int) q;
                b = (int) v;
            }
            case 4 -> {
                r = (int) t;
                g = (int) p;
                b = (int) v;
            }
            default -> {
                r = (int) v;
                g = (int) p;
                b = (int) q;
            }
        }
        return new int[]{r, g, b};
    }

    private void showError() {
        JOptionPane.showMessageDialog(this, "Invalid input, please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ColorConverterApp2::new);
    }
}
