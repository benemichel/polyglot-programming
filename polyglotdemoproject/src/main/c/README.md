Native code has to be compiled to LLVM Bitcode first to be executed by GraalVM.

See: https://www.graalvm.org/latest/reference-manual/llvm/Compiling/

``
$LLVM_TOOLCHAIN/clang++  MainProcessPayment.cpp PaymentTerminal.cpp -o MainProcessPayment.so
``