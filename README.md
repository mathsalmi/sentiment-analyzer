Sentiment Analyzer
===

A web app used to infer sentiments expressed in texts. It uses [SentiWordNet](http://sentiwordnet.isti.cnr.it), a sentiment analysis technique.

It initially supports English and Brazilian Portuguese but it is possible to register new languages and train the system to infer sentiments.

Training Process:
---

The system uses a dictionary with the words of each language it aims to support. These words must be registered by an administrator before the system is capable to analyze texts written in such languages.

For the English language it's used the dictionary provided by the [SentiWordNet](http://sentiwordnet.isti.cnr.it) project, so many thanks to them for making available such great work.

Votes:
---

After the words are registered there is one more step before using the system: classifying each word.

Because this is such a huge effort the system has a public section in which users will see words and concepts randomnly so they can vote by choosing the percent of positivity, negative or objectivity (neutral).

Ideas:
---

Currently the algorithm uses each word to extract the sentiments but that is not suitable for every language. 

For example: in Portuguese words vary in gender and number, verbs have different conjugation for each pronoun and that conjugation changes depending on the tense used. This makes very difficult to produce good results with the current implementation so improving the algorithm is pretty important.

Screenshots:
---

### Home
![Home](https://raw.githubusercontent.com/mathsalmi/snet/master/screenshot-1.png)

### Inferring texts
![Results](https://raw.githubusercontent.com/mathsalmi/snet/master/screenshot-2.png)

### Voting page
![Voting page](https://raw.githubusercontent.com/mathsalmi/snet/master/screenshot-3.png)
