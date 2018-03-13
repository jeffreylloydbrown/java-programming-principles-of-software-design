public class PhraseFilter implements Filter {
    private String myWhere;
    private String myPhrase;
    private String myName;

    public PhraseFilter (String where, String phrase) { init(null, where, phrase); }

    public PhraseFilter (String name, String where, String phrase) { init(name, where, phrase); }

    private void init (String name, String where, String phrase) {
        myWhere = where;
        myPhrase = phrase;
        myName = (name == null || name.isEmpty()) ? "Phrase" : name;
    }

    public String getName() { return myName; }

    public boolean satisfies (QuakeEntry qe) {
        if (myWhere == null || !(myWhere.equals("start") || myWhere.equals("any") || myWhere.equals("end")))
            return false;
        if (myPhrase == null || myPhrase.isEmpty()) return false;
        if (qe == null) return false;
        // don't forget checking qe.getInfo
        String info = qe.getInfo();
        if (info == null || info.isEmpty()) return false;

        if (myWhere.equals("start"))
            return info.startsWith(myPhrase);
        else if (myWhere.equals("end"))
            return info.endsWith(myPhrase);
        else
            return info.contains(myPhrase);
    }
}
