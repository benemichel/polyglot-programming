#include <cstdlib>
#include "PaymentTerminal.h"

int processPayment(int id) {
    PaymentTerminal terminal;
    return terminal.processPayment(id);
}

int main(int argc, char *argv[]) {
    int id = std::atoi(argv[1]);
    
    return processPayment(id);
}
