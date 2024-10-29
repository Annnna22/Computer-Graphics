#include "rasteralgorithms.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    RasterAlgorithms w;
    w.show();
    return a.exec();
}
