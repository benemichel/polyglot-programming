class WithFakeOverloadedMethod():
  
    def concat(*args):
        if len(args) == 0:
            return ''
        elif len(args) == 1:
            return str(args[0])
        elif len(args) == 2:
            if isinstance(args[1], str):
                return args[0] + args[1]
            else:
                 return args[0] + '%i' + str(args[1])
        else:
            raise TypeError(f"Unexpected arguments {args}")
        