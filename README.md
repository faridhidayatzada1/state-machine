┌─────────────────────────────────────────────────────────────────┐
│                     STATE MACHINE - GENERAL FLOW                │
└─────────────────────────────────────────────────────────────────┘

CommandLineRunner
│
│  account.save()
▼
┌─────────┐
│  DRAFT  │◄─── Initial status
└─────────┘
│
│  transaction("submit")
▼
┌─────────────────────────────────────────────────────────────────┐
│                   TransactionServiceImpl                        │
│                                                                 │
│  1. validateTransaction("submit")                               │
│     └─► transactionMap.containsKey("submit")? ──► NO → ERROR    │
│                                          │                      │
│                                          └─► yes → continue     │
│                                                                 │
│  2. retrieveAccount(id)                                         │
│     └─► DB find id → NO → ACCOUNT_NOT_FOUND error               │
│                                                                 │
│  3. getTransaction(id, "submit")                                │
│     └─► account.getAccountStatus().getTransactions()            │
│         ["submit"] ── account-un icazəli əməliyyatlarında       │
│                        "submit" var? → YOX → CANT_TRANSACT xəta │
│                                      │                          │
│                                      └─► HƏ → Submit bean-ini al│
│                                                                 │
│  4. transaction.applyProcessing(accountDto)                     │
│     └─► log.info("transitioning to in_review...")               │
│                                                                 │
│  5. updateStatus(account, APPROVED/IN_REVIEW/...)               │
│     └─► account.setAccountStatus(targetStatus)                  │
│         accountRepository.save(account)                         │
└─────────────────────────────────────────────────────────────────┘
│
▼
┌───────────┐
│ IN_REVIEW │
└───────────┘
│
│  transaction("approve")
▼
┌──────────┐
│ APPROVED │◄──────────┐
└──────────┘           │
│                 │ transaction("approve")
└─────────────────┘  (eyni statusda qala bilər)
│
│  transaction("notify")
▼
┌──────────┐
│ NOTIFIED │ ──► Son status, heç bir əməliyyat yoxdur
└──────────┘


┌─────────────────────────────────────────────────────────────────┐
│              STATUS → İCAZƏLİ ƏMƏLİYYATLAR                     │
├──────────────┬──────────────────────────────────────────────────┤
│ DRAFT        │  submit  ──────────────────────► IN_REVIEW       │
├──────────────┼──────────────────────────────────────────────────┤
│ IN_REVIEW    │  approve ──────────────────────► APPROVED        │
│              │  reject  ──────────────────────► (Reject bean)   │
├──────────────┼──────────────────────────────────────────────────┤
│ APPROVED     │  approve ──────────────────────► APPROVED        │
│              │  notify  ──────────────────────► NOTIFIED        │
├──────────────┼──────────────────────────────────────────────────┤
│ NOTIFIED     │  (boş) - heç bir əməliyyata icazə yoxdur        │
└──────────────┴──────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    BEAN / COMPONENT STRUKTURU                    │
│                                                                  │
│  Transition interface                                            │
│       │                                                          │
│       ├── Submit    (getName → "submit",  target → IN_REVIEW)   │
│       ├── Approve   (getName → "approve", target → APPROVED)    │
│       ├── Reject    (getName → "reject",  target → ?)           │
│       └── Notify    (getName → "notify",  target → NOTIFIED)    │
│                                                                  │
│  @PostConstruct → transactionMap:                                │
│       { "submit"  → Submit bean,                                 │
│         "approve" → Approve bean,                                │
│         "reject"  → Reject bean,                                 │
│         "notify"  → Notify bean  }                               │
└─────────────────────────────────────────────────────────────────┘