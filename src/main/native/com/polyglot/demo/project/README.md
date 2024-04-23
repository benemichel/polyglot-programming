Native code has to be compiled to LLVM Bitcode first to be executed by GraalVM.

See: https://www.graalvm.org/latest/reference-manual/llvm/Compiling/

Get the location of the toolchain, using the --print-toolchain-path argument of lli:
 ./path/to/bin/lli --print-toolchain-path
Copy
Set the LLVM_TOOLCHAIN environment variable:
 export LLVM_TOOLCHAIN=$(./path/to/bin/lli --print-toolchain-path)
Copy
Then see the content of the toolchain path for a list of available tools:
 ls $LLVM_TOOLCHAIN

export LLVM_TOOLCHAIN=$(/home/benedikt/masterarbeit/native/llvm-23.1.1-linux-amd64/bin/lli --
print-toolchain-path)

``
$LLVM_TOOLCHAIN/clang++  MainProcessPayment.cpp PaymentTerminal.cpp -o MainProcessPayment.so
``

to include polyglot.h run
``
$LLVM_TOOLCHAIN/clang -shared example.c -lpolyglot-mock -o example.so
``

for polyglot.h see
https://github.com/oracle/graal/blob/master/sulong/projects/com.oracle.truffle.llvm.libraries.graalvm.llvm/include/graalvm/llvm/polyglot.h