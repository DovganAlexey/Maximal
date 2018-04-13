package com.enzo.testaufgabe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by enzo on 13.04.18.
 */

public class Company implements Serializable {
    @SerializedName("name")
    @Expose
    private String companyName;
    @SerializedName("catchPhrase")
    @Expose
    private String catchPhrase;
    @SerializedName("bs")
    @Expose
    private String businessServices;

    public Company(String companyName, String catchPhrase, String businessServices) {
        this.companyName = companyName;
        this.catchPhrase = catchPhrase;
        this.businessServices = businessServices;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBusinessServices() {
        return businessServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!companyName.equals(company.companyName)) return false;
        if (!catchPhrase.equals(company.catchPhrase)) return false;
        return businessServices.equals(company.businessServices);
    }

    @Override
    public int hashCode() {
        int result = companyName.hashCode();
        result = 31 * result + catchPhrase.hashCode();
        result = 31 * result + businessServices.hashCode();
        return result;
    }
}