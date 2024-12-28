package com.st61019.SemA.dao.request;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class MailDetails {

    private String recipient;
    private String subject;
    private String message;
}