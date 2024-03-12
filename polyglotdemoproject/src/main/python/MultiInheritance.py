class A:
    def print(self):
        return 'class A'
    
class B:
    def print(self):
        return 'class B'

class C(A, B):
    pass


c = C()