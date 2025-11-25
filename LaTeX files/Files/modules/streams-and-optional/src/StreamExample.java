List<String> strings = ...;

//stream generieren
String mails = strings.stream()
                //nach einem Regex filtern
                .filter(str -> str.matches(".*@kit\\.edu"))
                //Von jeder E-Mail den Nutzernamen rausschneiden
                .map(str -> {
                        Matcher m = Pattern.compile("(.*)(@kit\\.edu)").matcher(str);
                        return m.group(0);
                })
                //alles zusammenkonkatenieren
                .reduce("", String::concat);
       