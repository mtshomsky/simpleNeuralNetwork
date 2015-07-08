simpleNeuralNetwork
===================




Summary:

Project follows a reasonable abstraction to support a variety of
different neuronal training.  Currently only feed-forward training is
implemented and is dated (started ~2003).  Future work would be to modernize the
project and add the remaining training capabilities.


Why this library:

- Construct an object oriented analog to neural networks
- Exploring the complexities of training 

Why not this library:

- There are faster better implementations of neural networks out there, this was merely for personal exploration.
- This lib was for architecture exploration of a common NN


Run Tests 
=========
- See: src/UT_adaline.java

Run AWT 2D-GUI
=========
- See: src/AdalineGUI.java

Modernization Todo List
=========
- Remove typecasting
- add junit test (pull apart UT_adaline.java)
- Replace adalineGUI with Jetty + d3 visualization (webify the project)
- ant

Training Todo List
=========
- Add additional networks


License
=========

The MIT License (MIT)

Copyright (c) 2014 Michael Shomsky

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
