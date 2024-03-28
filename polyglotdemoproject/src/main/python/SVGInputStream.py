import java

class SVGInputStream(java.io.InputStream):
  
    def read(self, *args):
        return 0
        # if len(args) == 0:
        #     return 0
        #     # read() 
        # elif len(args) == 1:
        #     return 1
        #     # read(byte[]) 
        # elif len(args) == 3:
        #     return 3
        #     # read(byte[], int off, int len) 
        # else:
        #     raise TypeError(f"Unexpected arguments {args}")
    
    def next_signed_byte(self):
        b = next(self.bytestream)
        if b > 127:
            return b - 256
        else:
            return b
