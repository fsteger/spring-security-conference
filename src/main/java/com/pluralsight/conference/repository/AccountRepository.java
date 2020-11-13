package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Account;

public interface AccountRepository {
    public Account create(Account account);
}
