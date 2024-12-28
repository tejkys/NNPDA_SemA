package com.st61019.SemA.service;


import com.st61019.SemA.dao.request.MailDetails;

public interface MailService {
    String sendSimpleMail(MailDetails details);

}