# Search Engine

## Assignment Summary
A search engine contains a set of documents. Each document consists of a unique ID and a list of tokens. The search engine responds to queries by finding documents which contain certain tokens and returning their IDs. The program should accept a sequence of commands from standard input and respond on standard output. Commands are terminated by newlines. </br>

There are two types of commands:
<ol>
  <li>Index Command</li>
  <li>Query Command</li>
</ol>
</br>

## Search Engine Commands

### 1. The Index Command
<b><em>index</b> doc-id token1 ... tokenN</em></br>

#### Rules of the index command are:</br>
<ul>
  <li>The index command adds a document to the index.</li>
  <li>The doc-id is an integer.</li>
  <li>Tokens are arbitrary alphanumeric strings.</li>
  <li>A document can contain an arbitrary number of tokens greater than zero.</li>
  <li>The same token may occur more than once in a document.</li>
  <li>If the doc-id in an index command is the same as in a previously seen index command, the previously stored document should be completely replaced (i.e., only the tokens from the latest command should be associated with the doc-id).</li>
</ul>

#### Result:
<ul>
  <li>When the program successfully processes an index command, it should output:</br> 
  <b><em>index ok</b> doc-id</em></li>
  <li>If the program sees an invalid index command (e.g, a token contains a non-alphanumeric character, or a doc-id is not an integer) it should report an error to standard output and continue processing commands. The error output should have the following form: </br>
  <b><em>index error</b> error message</em></li>
</ul>
    
#### Examples:
- index 1 soup tomato cream salt</br>
  - index ok 1
- index 2 cake sugar eggs flour sugar cocoa cream butter</br>
  - index ok 2
- index 1 bread butter salt</br>
  - index ok 1
- index 3 soup fish potato salt pepper</br>
  - index ok 3</br>


### 2. The Query Command
<em>query</b> expression</em></br>

#### Rules of the query command are:</br>

<ul>
  <li>Where expression is an arbitrary expression composed of alphanumeric tokens and the special symbols &, |, (, and ).</li>
  <li>The most simple expression is a single token, and the result of executing this query is a list of the IDs of the documents that contain the token.</li>
</ul></br>

<ul><b>Special Symbols --> &, |</b>
  <li>More complex expressions can be built built using the operations of set conjunction (denoted by &) and disjunction (denoted by |).</li>
  <li>The & and | operation have equal precedence and are commutative and associative.</li>
</ul></br>

<ul><b>Parenthesus --> (, )</b>
  <li>Parentheses have the standard meaning.</li>
  <li>Parentheses are mandatory: a | b | c is not valid, (a | b) | c must be used (this is to make parsing queries simpler).</li>
</ul>

#### Result:
<ul>
  <li>Logically, to execute the query the program looks at every document previously specified by the index command, checks if the document matches the query, and outputs the doc-id if it does.</li>
  <li>However this is suboptimal and much more efficient implementations exist.</li>
  <li>Upon reading the query command the program should execute the query and produce the following output (The doc-ids of the matching documents can be output in arbitrary order):</br>
  <b><em>query results</b> doc-id1 doc-id2 ...</em></li>
  <li>If there is an error, the output should be:</br>
  <b><em>query error</b> error message</em></li>
</ul>


#### Examples:
Examples, given the index commands shown in the example above:</br>

- in: query butter
  - out: query results 2 1
- in: query sugar
  - out: query results 2
- in: query soup
  - out: query results 3
- in: query (butter | potato) & salt
  - out: query results 1 3

## Requirements of the Assignment
Implement a search engine in Java, using some of the data structures you know. You  may use standard and open-source libraries and you may consult online materials or books. Please try to make the implementation reasonably fast, for a fairly large number of documents,say in the tens of thousands (you don’t need to test it on large sets of documents, just think about what this would be for your design). Query speed is more important than indexing speed.
