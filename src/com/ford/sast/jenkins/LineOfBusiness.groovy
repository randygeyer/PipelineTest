package com.ford.sast.jenkins

class LineOfBusiness {

    enum LoB {
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
    
        LoB(String fullname) {
            this.fullname = fullname
        }
    
        private final String fullname
        String getFullname() {
            fullname
        }
    }
    
    static final String AV = LoB.AV
    static final String CV = LoB.CV
    static final String EV = LoB.EV
    static final String FordCredit = LoB.FordCredit
    static final String FordPass = LoB.FordPass
    static final String FoundationalServices = LoB.FoundationalServices
    static final String FSM = LoB.FSM
    static final String GDIA = LoB.GDIA
    static final String IT = LoB.IT
    
    static def LoB parse(String lob) {
        return lob as LoB
    }
}
