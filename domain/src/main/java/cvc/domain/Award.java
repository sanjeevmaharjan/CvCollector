package cvc.domain;

import javax.persistence.*;

@Entity
public class Award extends PersistentObject{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private Cv Cv;

    private String Title;

    private short Year;

    private String AwardFor;

    public Award() {
    }

    public Award(String title, short year, String awardFor) {
        Title = title;
        Year = year;
        AwardFor = awardFor;
    }

    //region getters and setters

    public String getTitle() {
        return Title;
    }

    public short getYear() {
        return Year;
    }

    public String getAwardFor() {
        return AwardFor;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setYear(short year) {
        Year = year;
    }

    public void setAwardFor(String awardFor) {
        AwardFor = awardFor;
    }

    //endregion getters and setters
}
