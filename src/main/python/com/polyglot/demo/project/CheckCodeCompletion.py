import polyglot


js_code = """let foo; foo.x = 5; foo.y = 10; foo"""

foo = polyglot.eval(language='js', string=js_code);
foo.x