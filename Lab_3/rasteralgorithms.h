#ifndef RASTERALGORITHMS_H
#define RASTERALGORITHMS_H

#include <QtWidgets>
#include "qcustomplot.h"

class RasterAlgorithms : public QWidget
{
    Q_OBJECT
public:
    RasterAlgorithms(QWidget *parent = nullptr);
    ~RasterAlgorithms();

private:
    QCustomPlot *wgt;

    QHBoxLayout *mainLayout;

    QGroupBox *algorithms;
    QGroupBox *data;
    QGroupBox *graphButtons;

    QRadioButton *stepByStepRadBt;
    QRadioButton *cdaRadBt;
    QRadioButton *bresenhamLineRadBt;
    QRadioButton *bresenhamCircleRadBt;
    QRadioButton *castlePitwayRadBt;

    QCheckBox *smoothedLineCkBx;

    QLabel *mainAlgorithmLbl;
    QLabel *mainDataLbl;

    QLabel *x0Lbl;
    QLabel *y0Lbl;
    QLabel *x1Lbl;
    QLabel *y1Lbl;
    QLabel *radiusLbl;

    QLineEdit *x0LnEd;
    QLineEdit *y0LnEd;
    QLineEdit *x1LnEd;
    QLineEdit *y1LnEd;
    QLineEdit *radiusLnEd;

    QPushButton *drawGraphic;

    QPushButton *toStart;
    QPushButton *toGraphic;
    QPushButton *clearGraphic;

    int lastX0;
    int lastY0;
    int lastX1;
    int lastY1;
    int lastR;

    int currentGraph;

    QVector<int> xCoord;
    QVector<int> yCoord;

    void lineOrCircleDataVisible(bool line);

    void stepAlgorithm(int x0, int y0, int x1, int y1);
    void ddaAlgorithm(int x0, int y0, int x1, int y1);
    void bresenhamLineAlgorithm(int x0, int y0, int x1, int y1);
    void bresenhamCircleAlgorithm(int x0, int y0, int rad);
    void castlePitwayAlgorithm(int x0, int y0, int x1, int y1);

private slots:
    void lineData();
    void circleData();

    void goToOrigin();
    void goToGraphic();
    void clearAllGraphics();

    void drawPlot();
};

#endif // RASTERALGORITHMS_H
