package com.ford.sast.jenkins

enum LineOfBusiness {

    // TODO: replace names with proper full names
    AV ('AV'),
    CV('CV'),
    EV('EV'),
    FordCredit('FordCredit'),
    FordPass('FordPass'),
    FoundationalServices('FoundationalServices'),
    FSM('FSM'),
    GDIA('GDIA'),
    IT('IT')

    LineOfBusiness(String fullname) {
        this.fullname = fullname
    }

    private final String fullname
    String getFullname() {
        fullname
    }
}
