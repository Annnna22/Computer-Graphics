#include "rasteralgorithms.h"

RasterAlgorithms::RasterAlgorithms(QWidget *parent): QWidget{parent}
{
    qApp->setStyle(QStyleFactory::create("Fusion"));
    setWindowTitle("Raster Algorithms");
    setMinimumSize(600, 430);

    currentGraph = -1;

    wgt = new QCustomPlot;
    wgt->setSizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
    wgt->setInteraction(QCP::iRangeDrag,true);
    wgt->setInteraction(QCP::iRangeZoom,true);
    goToOrigin();

    QFont font;
    font.setBold(true);

    mainAlgorithmLbl = new QLabel("ALGORITHMS");
    mainAlgorithmLbl->setAlignment(Qt::AlignCenter);
    mainAlgorithmLbl->setFont(font);
    mainDataLbl = new QLabel("DATA");
    mainDataLbl->setAlignment(Qt::AlignCenter);
    mainDataLbl->setFont(font);

    stepByStepRadBt = new QRadioButton("Step-by-step");
    stepByStepRadBt->setFont(font);
    cdaRadBt = new QRadioButton("DDA");
    cdaRadBt->setFont(font);
    bresenhamLineRadBt = new QRadioButton("Bresenham line");
    bresenhamLineRadBt->setFont(font);
    bresenhamCircleRadBt = new QRadioButton("Bresenham circle");
    bresenhamCircleRadBt->setFont(font);
    castlePitwayRadBt = new QRadioButton("Castle-Pitway");
    castlePitwayRadBt->setFont(font);
    smoothedLineCkBx = new QCheckBox("Smoothed line");
    smoothedLineCkBx->setFont(font);
    smoothedLineCkBx->setChecked(true);

    x0Lbl = new QLabel("x1:");
    x0Lbl->setFont(font);
    y0Lbl = new QLabel("y1:");
    y0Lbl->setFont(font);
    x1Lbl = new QLabel("x2:");
    x1Lbl->setFont(font);
    y1Lbl = new QLabel("y2:");
    y1Lbl->setFont(font);
    radiusLbl = new QLabel("r:");
    radiusLbl->setFont(font);

    QValidator *coordValid = new QRegularExpressionValidator(QRegularExpression("^([-]?[1-9][0-9]*)|(0)$"));
    QValidator *radValid = new QRegularExpressionValidator(QRegularExpression("^([1-9][0-9]*)$"));

    x0LnEd = new QLineEdit("0");
    x0LnEd->setValidator(coordValid);
    y0LnEd = new QLineEdit("0");
    y0LnEd->setValidator(coordValid);
    x1LnEd = new QLineEdit("400");
    x1LnEd->setValidator(coordValid);
    y1LnEd = new QLineEdit("400");
    y1LnEd->setValidator(coordValid);
    radiusLnEd = new QLineEdit("250");
    radiusLnEd->setValidator(radValid);

    x0LnEd->setFixedWidth(50);
    y0LnEd->setFixedWidth(50);
    x1LnEd->setFixedWidth(50);
    y1LnEd->setFixedWidth(50);
    radiusLnEd->setFixedWidth(50);

    toStart = new QPushButton("Origin");
    toStart->setFont(font);
    toGraphic = new QPushButton("To Last Graphic");
    toGraphic->setFont(font);
    clearGraphic = new QPushButton("Clear");
    clearGraphic->setFont(font);



    QVBoxLayout *algorithmsLay = new QVBoxLayout;
    algorithmsLay->addWidget(mainAlgorithmLbl);
    algorithmsLay->addWidget(stepByStepRadBt);
    algorithmsLay->addWidget(cdaRadBt);
    algorithmsLay->addWidget(bresenhamLineRadBt);
    algorithmsLay->addWidget(bresenhamCircleRadBt);
    algorithmsLay->addWidget(castlePitwayRadBt);
    algorithmsLay->addWidget(smoothedLineCkBx);

    QHBoxLayout *firstPointData = new QHBoxLayout();
    firstPointData->addWidget(x0Lbl);
    firstPointData->addWidget(x0LnEd);
    firstPointData->addWidget(y0Lbl);
    firstPointData->addWidget(y0LnEd);

    QHBoxLayout *secondPointData = new QHBoxLayout();
    secondPointData->addWidget(x1Lbl);
    secondPointData->addWidget(x1LnEd);
    secondPointData->addWidget(y1Lbl);
    secondPointData->addWidget(y1LnEd);

    QHBoxLayout *radiusData = new QHBoxLayout();
    radiusData->addWidget(radiusLbl);
    radiusData->addSpacing(8);
    radiusData->addWidget(radiusLnEd);
    radiusData->addStretch(1);

    drawGraphic = new QPushButton("DRAW");
    drawGraphic->setFont(font);

    QGridLayout *dataLay = new QGridLayout;
    dataLay->addWidget(mainDataLbl, 0, 0, 1, 3);
    dataLay->addLayout(firstPointData, 1, 0, 1, 3);
    dataLay->addLayout(secondPointData, 2, 0, 1, 3);
    dataLay->addLayout(radiusData, 3, 0, 1, 3);
    dataLay->addWidget(drawGraphic, 4, 1);

    QHBoxLayout *buttons = new QHBoxLayout;
    buttons->addWidget(toStart);
    buttons->addWidget(toGraphic);
    buttons->addWidget(clearGraphic);

    algorithms = new QGroupBox;
    algorithms->setLayout(algorithmsLay);
    data = new QGroupBox;
    data->setLayout(dataLay);
    data->setSizePolicy(QSizePolicy::Minimum, QSizePolicy::Minimum);
    graphButtons = new QGroupBox;
    graphButtons->setLayout(buttons);


    QVBoxLayout *leftPart = new QVBoxLayout();
    leftPart->addWidget(algorithms);
    leftPart->addWidget(data);
    leftPart->addStretch(1);
    QVBoxLayout *rightPart = new QVBoxLayout;
    rightPart->addWidget(wgt);
    rightPart->addWidget(graphButtons);

    mainLayout = new QHBoxLayout(this);
    mainLayout->addLayout(leftPart);
    mainLayout->addLayout(rightPart);

    connect(stepByStepRadBt, &QRadioButton::clicked, this, &RasterAlgorithms::lineData);
    connect(cdaRadBt, &QRadioButton::clicked, this, &RasterAlgorithms::lineData);
    connect(bresenhamLineRadBt, &QRadioButton::clicked, this, &RasterAlgorithms::lineData);
    connect(bresenhamCircleRadBt, &QRadioButton::clicked, this, &RasterAlgorithms::circleData);
    connect(castlePitwayRadBt, &QRadioButton::clicked, this, &RasterAlgorithms::lineData);

    stepByStepRadBt->setChecked(true);
    emit stepByStepRadBt->clicked();

    connect(toStart, &QPushButton::clicked, this, &RasterAlgorithms::goToOrigin);
    connect(toGraphic, &QPushButton::clicked, this, &RasterAlgorithms::goToGraphic);
    connect(clearGraphic, &QPushButton::clicked, this, &RasterAlgorithms::clearAllGraphics);

    connect(drawGraphic, &QPushButton::clicked, this, &RasterAlgorithms::drawPlot);
}

RasterAlgorithms::~RasterAlgorithms(){}

void RasterAlgorithms::lineOrCircleDataVisible(bool line)
{
    x1LnEd->setVisible(line);
    x1Lbl->setVisible(line);
    y1LnEd->setVisible(line);
    y1Lbl->setVisible(line);

    radiusLnEd->setVisible(!line);
    radiusLbl->setVisible(!line);
}

void RasterAlgorithms::lineData()
{
    lineOrCircleDataVisible(true);
}

void RasterAlgorithms::circleData()
{
    lineOrCircleDataVisible(false);
}

void RasterAlgorithms::goToOrigin()
{
    wgt->xAxis->setRange(-1000, 1000);
    wgt->yAxis->setRange(-1000, 1000);
    wgt->replot();
}

void RasterAlgorithms::goToGraphic()
{
    if (wgt->graphCount() == 0)
    {
        QMessageBox::information(this, "Information", "There are no graphics on the plot");
        return;
    }

    if (lastR != -1)
    {
        wgt->xAxis->setRange(lastX0 - 2*lastR, lastX0 + 2*lastR);
        wgt->yAxis->setRange(lastY0 - 2*lastR, lastY0 + 2*lastR);
    }
    else
    {
        int x = abs(lastX1 - lastX0)/3;
        int y = abs(lastY1 - lastY0)/3;
        int r = x > y ? x : y;
        wgt->xAxis->setRange(lastX0 - r, lastX1 + r);
        wgt->yAxis->setRange(lastY0 - r, lastY1 + r);
    }
    wgt->replot();
}

void RasterAlgorithms::clearAllGraphics()
{
    wgt->clearGraphs();
    currentGraph = -1;
    wgt->replot();
}

void RasterAlgorithms::drawPlot()
{
    int x0 = x0LnEd->text().toInt();
    int x1 = x1LnEd->text().toInt();
    int y0 = y0LnEd->text().toInt();
    int y1 = y1LnEd->text().toInt();
    int rad = radiusLnEd->text().toInt();
    currentGraph++;
    wgt->addGraph();

    if (smoothedLineCkBx->isChecked())
        wgt->graph(currentGraph)->setAntialiased(true);
    else
        wgt->graph(currentGraph)->setAntialiased(false);

    if (bresenhamCircleRadBt->isChecked())
    {
        lastX0 = x0;
        lastY0 = y0;
        lastR = rad;
        bresenhamCircleAlgorithm(x0, y0, rad);
    }
    else
    {
        lastX0 = x0 < x1 ? x0 : x1;
        lastY0 = y0 < y1 ? y0 : y1;
        lastX1 = x0 < x1 ? x1 : x0;
        lastY1 = y0 < y1 ? y1 : y0;
        lastR = -1;

        if (stepByStepRadBt->isChecked())
            stepAlgorithm(x0, y0, x1, y1);
        else if (cdaRadBt->isChecked())
            ddaAlgorithm(x0, y0, x1, y1);
        else if (bresenhamLineRadBt->isChecked())
            bresenhamLineAlgorithm(x0, y0, x1, y1);
        else
            castlePitwayAlgorithm(x0, y0, x1, y1);
    }
}

void RasterAlgorithms::stepAlgorithm(int x0, int y0, int x1, int y1)
{
    if (x0 > x1)
    {
        std::swap(x0, x1);
        std::swap(y0, y1);
    }

    if (x0 == x1)
    {
        wgt->graph(currentGraph)->addData(x0, y0);
        wgt->graph(currentGraph)->addData(x1, y1);
        wgt->replot();
    }
    else
    {
        double k = (double)(y1-y0)/(double)(x1-x0);
        double b = y0 - k * x0;
        wgt->graph(currentGraph)->addData(x0, y0);
        int x = x0;
        int y = y0;
        while(x < x1)
        {
            x++;
            y=round(k * x + b);
            wgt->graph(currentGraph)->addData(x, y);
            wgt->replot();
        }
    }
}

void RasterAlgorithms::ddaAlgorithm(int x0, int y0, int x1, int y1)
{
    if (x0 > x1)
    {
        std::swap(x0, x1);
        std::swap(y0, y1);
    }

    float x = x0;
    float y = y0;
    float dx = x1 - x0;
    float dy = y1 - y0;
    float l = std::max(dx, dy);
    wgt->graph(currentGraph)->addData(x0, y0);
    int i = 0;
    while (i < l)
    {
        y += dy/l;
        x += dx/l;
        wgt->graph(currentGraph)->addData(trunc(x), trunc(y));
        wgt->replot();
        ++i;
    }
}

void RasterAlgorithms::bresenhamLineAlgorithm(int x0, int y0, int x1, int y1)
{
    bool steep = abs(y1 - y0) > abs(x1 - x0);
    if (steep)
    {
        std::swap(x0, y0);
        std::swap(x1, y1);
    }

    if (x0 > x1)
    {
        std::swap(x0, x1);
        std::swap(y0, y1);
    }

    int dx = x1 - x0;
    int dy = abs(y1 - y0);
    int error = dx / 2;
    int ystep = (y0 < y1) ? 1 : -1;
    int y = y0;
    for (int x = x0; x <= x1; x++)
    {
        wgt->graph(currentGraph)->addData(steep ? y : x, steep ? x : y);
        wgt->replot();
        error -= dy;
        if (error < 0)
        {
            y += ystep;
            error += dx;
        }
    }
}

void RasterAlgorithms::bresenhamCircleAlgorithm(int x0, int y0, int rad)
{
    if (rad == 1)
    {
        wgt->addGraph();
        wgt->addGraph();
        wgt->addGraph();

        wgt->graph(currentGraph)->addData (x0 + 1, y0);
        wgt->graph(currentGraph)->addData (x0, y0 + 1);
        wgt->graph(currentGraph+1)->addData (x0, y0 + 1);
        wgt->graph(currentGraph+1)->addData (x0 - 1, y0);
        wgt->graph(currentGraph+2)->addData (x0 - 1, y0);
        wgt->graph(currentGraph+2)->addData (x0, y0 - 1);
        wgt->graph(currentGraph+3)->addData (x0, y0 - 1);
        wgt->graph(currentGraph+3)->addData (x0 + 1, y0);
        wgt->replot();
        currentGraph += 3;
        return;
    }

    wgt->addGraph();
    wgt->addGraph();
    wgt->addGraph();
    wgt->addGraph();
    wgt->addGraph();
    wgt->addGraph();
    wgt->addGraph();

    int x = rad;
    int y = 0;
    int radiusError = 1 - x;

    wgt->graph(currentGraph+1)->addData(y + x0, x + y0);
    wgt->graph(currentGraph+2)->addData(-x + x0, y + y0);
    wgt->graph(currentGraph+4)->addData(-x + x0, -y + y0);
    wgt->graph(currentGraph+7)->addData(y + x0, -x + y0);

    xCoord.push_back(x);
    yCoord.push_back(y);

    while (x >= y)
    {
        y++;
        if (radiusError < 0)
        {
            radiusError += 2 * y + 1;
        }
        else
        {
            x--;
            radiusError += 2 * (y - x + 1);
        }
        wgt->graph(currentGraph+1)->addData(y + x0, x + y0);
        wgt->graph(currentGraph+2)->addData(-x + x0, y + y0);
        wgt->graph(currentGraph+4)->addData(-x + x0, -y + y0);
        wgt->graph(currentGraph+7)->addData(y + x0, -x + y0);
        wgt->replot();

        xCoord.push_back(x);
        yCoord.push_back(y);
    }

    for(int i = xCoord.size() - 1; i >= 0; i--)
    {
        wgt->graph(currentGraph)->addData(xCoord[i] + x0, yCoord[i] + y0);
        wgt->graph(currentGraph+3)->addData(-yCoord[i] + x0, xCoord[i]  + y0);
        wgt->graph(currentGraph+5)->addData(-yCoord[i] + x0, -xCoord[i]  + y0);
        wgt->graph(currentGraph+6)->addData(xCoord[i]  + x0, -yCoord[i] + y0);
        wgt->replot();
    }

    currentGraph+=7;
    xCoord.clear();
    yCoord.clear();
}

void RasterAlgorithms::castlePitwayAlgorithm(int x0, int y0, int x1, int y1)
{
    bool steep = abs(y1 - y0) > abs(x1 - x0);
    if (steep)
    {
        std::swap(x0, y0);
        std::swap(x1, y1);
    }
    if (x0 > x1)
    {
        std::swap(x0, x1);
        std::swap(y0, y1);
    }

    bool inverse = x0 < x1 && y0 > y1 ? true : false;
    int x = abs(abs(x1 - x0) - abs(y1 - y0));
    int y = abs(y1 - y0);
    if (x == 0)
    {
        bool up = y1 - y0 > 0;
        x = x0;
        y = y0;
        wgt->graph(currentGraph)->addData(steep ? y : x, steep ? x : y);
        while (x < x1)
        {
            x++;
            if (up)
                y++;
            else
                y--;
            wgt->graph(currentGraph)->addData(steep ? y : x, steep ? x : y);
            wgt->replot();
        }
        return;
    }

    QVector<char>m1, m2, temp;
    m1.push_back('s');
    m2.push_back('d');
    while(x != y)
    {
        if (x > y)
        {
            x -= y;
            temp = m2;
            std::reverse(temp.begin(), temp.end());
            m2 = m1 + temp;
        }
        else
        {
            y -= x;
            temp = m1;
            std::reverse(temp.begin(), temp.end());
            m1 = m2 + temp;
        }
    }
    std::reverse(m1.begin(), m1.end());
    temp = m2 + m1;
    int steps = x;
    x = x0;
    y = y0;
    wgt->graph(currentGraph)->addData(steep ? y0 : x0, steep ? x0 : y0);
    while (steps > 0)
    {
        for (int i = 0; i < temp.size(); i++)
        {
            if (temp[i] == 's')
                x++;
            else
            {
                x++;
                if(inverse)
                    y--;
                else
                    y++;
            }
            wgt->graph(currentGraph)->addData(steep ? y : x, steep ? x : y);
            wgt->replot();
        }
        steps--;
    }
}
