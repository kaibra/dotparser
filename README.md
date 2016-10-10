# dotparser
A small clojure library to parse dotfile-strings into a clojure format

`[kaibra/dotparser "0.0.1"]`

Current support:
*   Parsing digraphs

## Example 1


```
(require '[kaibra.dotparser :as dotparser])

(def dot-str
  "digraph G {
   1 [ label=\"A\" foo=\"bar\" ];
   2 [ label=\"B\" bar=\"baz\" ];
   3 [ label=\"C\" ];
   4 ;
   5 [ baf=\"bif\" bif=\"bum\" ];
   1 -> 2;
   2 -> 3;
   2 -> 4;
   4 -> 5;
  }")

(dotparser/parse-dotfile-str dot-str)

```

becomes 

```clj
{:1 {:label    "A"
    :foo      "bar"
    :children [:2]}
:2 {:label    "B"
    :bar      "baz"
    :children [:3 :4]}
:3 {:label    "C"
    :children []}
:4 {:children [:5]}
:5 {:baf      "bif"
    :bif      "bum"
    :children []}}

```

Kai Brandes