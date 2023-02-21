package com.metaltravelguide.places.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 299468828545931466L;
    @Column(name = "contact_telephone")
    private String telephone;
    @Column(name = "contact_mail")
    private String mail;
    @Column(name = "contact_website")
    private String website;
    @Column(name = "contact_facebook")
    private String facebook;
    @Column(name = "contact_instagram")
    private String instagram;
    @Column(name = "contact_twitter")
    private String twitter;
}
