package cvc.logic.model;

import cvc.domain.Cv;
import cvc.logic.specifications.ContactDetailsSpecifications;
import cvc.logic.specifications.PersonalDetailsSpecifications;
import enums.EGender;
import org.springframework.data.jpa.domain.Specification;

// Default values Considered in search logic. Not might be used though
public class CvSearchCriteria {
    // region Personal Details Criteria
    private String Name = null;

    private EGender Gender = EGender.Unspecified;

    // For Age
    private short MinAge = 0;
    private short MaxAge = 200;

    private String Nationality;

    private String MaritalStatus;

    private String CareerTitle;

    // endregion Personal Details Criteria

    // region Contact Details Criteria

    private String Country;

    private String City;

    private boolean RelocationCriteria = false;

    // endregion Contact Details Criteria

    // region Education Details Criteria

    private int AcademicScoreMin;

    private int AcademicScoreMax;

    // endregion Education Details Criteria

    // region Work Details Criteria

    private int JobExperienceMin;

    private int JobExperienceMax;

    // endregion Work Details Criteria

    // region Project Details Criteria

    private int project;

    // endregion Project Details Criteria

    // region Additional Fields Criteria

    private String PersonalAdditional;

    private String ContactAdditional;

    private String EducationAdditional;

    private String ProfessionalAdditional;

    private String ProjectAdditional;

    private String AdditionalNotes;

    // endregion Additional Fields Criteria

    private Specification<Cv> specs = null;

    // region getters and setters

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public EGender getGender() {
        return Gender;
    }

    public void setGender(EGender gender) {
        Gender = gender;
    }

    public short getMinAge() {
        return MinAge;
    }

    public void setMinAge(short minAge) {
        MinAge = minAge;
    }

    public short getMaxAge() {
        return MaxAge;
    }

    public void setMaxAge(short maxAge) {
        MaxAge = maxAge;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getCareerTitle() {
        return CareerTitle;
    }

    public void setCareerTitle(String careerTitle) {
        CareerTitle = careerTitle;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getAcademicScoreMin() {
        return AcademicScoreMin;
    }

    public void setAcademicScoreMin(int academicScoreMin) {
        AcademicScoreMin = academicScoreMin;
    }

    public int getAcademicScoreMax() {
        return AcademicScoreMax;
    }

    public void setAcademicScoreMax(int academicScoreMax) {
        AcademicScoreMax = academicScoreMax;
    }

    public int getJobExperienceMin() {
        return JobExperienceMin;
    }

    public void setJobExperienceMin(int jobExperienceMin) {
        JobExperienceMin = jobExperienceMin;
    }

    public int getJobExperienceMax() {
        return JobExperienceMax;
    }

    public void setJobExperienceMax(int jobExperienceMax) {
        JobExperienceMax = jobExperienceMax;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getPersonalAdditional() {
        return PersonalAdditional;
    }

    public void setPersonalAdditional(String personalAdditional) {
        PersonalAdditional = personalAdditional;
    }

    public String getContactAdditional() {
        return ContactAdditional;
    }

    public void setContactAdditional(String contactAdditional) {
        ContactAdditional = contactAdditional;
    }

    public String getEducationAdditional() {
        return EducationAdditional;
    }

    public void setEducationAdditional(String educationAdditional) {
        EducationAdditional = educationAdditional;
    }

    public String getProfessionalAdditional() {
        return ProfessionalAdditional;
    }

    public void setProfessionalAdditional(String professionalAdditional) {
        ProfessionalAdditional = professionalAdditional;
    }

    public String getProjectAdditional() {
        return ProjectAdditional;
    }

    public void setProjectAdditional(String projectAdditional) {
        ProjectAdditional = projectAdditional;
    }

    public String getAdditionalNotes() {
        return AdditionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        AdditionalNotes = additionalNotes;
    }

    public boolean isRelocationCriteria() {
        return RelocationCriteria;
    }

    public void setRelocationCriteria(boolean relocationCriteria) {
        RelocationCriteria = relocationCriteria;
    }

    // endregion getters and setters

    // region Other functions

    public Specification<Cv> getSpecs() {
        if (isNotNullOrEmpty(Name)) {
            AddSpec(PersonalDetailsSpecifications.nameStartsWith(Name));
        }

        if (!Gender.equals(EGender.Unspecified)) {
            AddSpec(PersonalDetailsSpecifications.thisGenderOnly(Gender));
        }

        if (MinAge > 0) {
            AddSpec(PersonalDetailsSpecifications.ageGreaterThan(MinAge));
        }

        if (MaxAge < 200 ) {
            AddSpec(PersonalDetailsSpecifications.ageLessThan(MaxAge));
        }

        if (RelocationCriteria) {
            AddSpec(ContactDetailsSpecifications.canRelocateOnly());
        }

        return specs;
    }

    private void AddSpec(Specification<Cv> spec) {
        if (spec == null) return;

        if (specs == null) {
            specs = spec;
            return;
        }

        specs = specs.and(spec);
    }

    private boolean isNotNullOrEmpty (String value) {
        return value != null && !value.isEmpty();
    }

    // endregion Other functions
}
