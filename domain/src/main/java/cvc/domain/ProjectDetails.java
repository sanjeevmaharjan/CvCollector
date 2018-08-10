package cvc.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ProjectDetails extends PersistentObject {

    @OneToOne(mappedBy = "Project")
    private Cv Cv;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Project> Projects;

    private String AdditionalDetails;

    public ProjectDetails() {}

    public ProjectDetails(List<Project> projects, String additionalDetails) {
        Projects = projects;
        AdditionalDetails = additionalDetails;
    }

    //region getters and setters

    public List<Project> getProjects() {
        return Projects;
    }

    public String getAdditionalDetails() {
        return AdditionalDetails;
    }

    public void setProjects(List<Project> projects) {
        Projects = projects;
    }

    public void setAdditionalDetails(String additionalDetails) {
        AdditionalDetails = additionalDetails;
    }

    //endregion getters and setters
}
