#include <graalvm/llvm/polyglot.h>

#include <stdlib.h>
#include <stdio.h>

struct Point {
    double x;
    double y;
};

POLYGLOT_DECLARE_STRUCT(Point)

void *allocNativePoint() {
    struct Point *ret = malloc(sizeof(*ret));
    return polyglot_from_Point(ret);
}

void *allocNativePointArray(int length) {
    struct Point *ret = calloc(length, sizeof(*ret));
    return polyglot_from_Point_array(ret, length);
}

void *allocNativeArray() {
    int32_t *s = calloc(5, sizeof(*s));
    s[0] = 1;
    s[1] = 2;
    s[2] = 3;
    s[3] = 4;
    s[4] = 5;
    
    return polyglot_from_i32_array(s, 5);
}

void freeNativePoint(struct Point *p) {
    free(p);
}

void printPoint(struct Point *p) {
    printf("Point<%f,%f>\n", p->x, p->y);
}
