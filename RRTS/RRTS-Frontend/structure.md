rrts-frontend/
│
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── services/
│   │   │   │   ├── auth.service.ts
│   │   │   │   ├── complaint.service.ts
│   │   │   │   ├── schedule.service.ts
│   │   │   │   └── ...other services
│   │   │   ├── guards/
│   │   │   │   ├── auth.guard.ts
│   │   │   │   └── role.guard.ts
│   │   │   └── interceptors/
│   │   │       ├── auth.interceptor.ts
│   │   │       └── error.interceptor.ts
│   │   │
│   │   ├── shared/
│   │   │   ├── components/
│   │   │   └── models/
│   │   │       ├── user.model.ts
│   │   │       ├── complaint.model.ts
│   │   │       └── ...other models
│   │   │
│   │   ├── features/
│   │   │   ├── complaints/
│   │   │   │   ├── complaints.component.ts
│   │   │   │   ├── complaint-details/
│   │   │   │   │   ├── complaint-details.component.ts
│   │   │   │   │   └── complaint-details.component.html
│   │   │   │   ├── create-complaint/
│   │   │   │   │   ├── create-complaint.component.ts
│   │   │   │   │   └── create-complaint.component.html
│   │   │   │   └── ...other complaint-related components
│   │   │   │
│   │   │   ├── dashboard/
│   │   │   │   ├── dashboard.component.ts
│   │   │   │   └── dashboard.component.html
│   │   │   │
│   │   │   ├── schedules/
│   │   │   │   ├── schedules.component.ts
│   │   │   │   └── ...other schedule-related components
│   │   │   │
│   │   │   └── reports/
│   │   │       ├── reports.component.ts
│   │   │       └── ...other report-related components
│   │   │
│   │   ├── app-routing.module.ts
│   │   └── app.component.ts
│   │
│   ├── assets/
│   ├── environments/
│   │   ├── environment.prod.ts
│   │   └── environment.ts
│   │
│   └── index.html
│
├── angular.json
└── package.json
