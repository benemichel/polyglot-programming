#include <stdlib.h>
#include <stdio.h>

struct Point {
    double x;
    double y;
};

void printPoint(struct Point *p) {
    printf("Point<%f,%f>\n", p->x, p->y);
}

int main() {
  struct Point point;
  point.x = 40;
  point.y = 2;
  printPoint(&point);
  return 42;
}

