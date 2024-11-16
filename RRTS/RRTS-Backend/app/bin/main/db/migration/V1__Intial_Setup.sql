/*------------------------------------------------- BUSINESS LOGIC HERE ----------------------------------------------------------*/

CREATE EXTENSION IF NOT EXISTS "pgcrypto"; -- Enables the gen_random_uuid() function

CREATE TABLE Areas (
    id SERIAL PRIMARY KEY,        -- Unique area ID
    area_name VARCHAR(100) NOT NULL   -- Area name or description
);

CREATE TABLE Users (
    id UUID primary key default gen_random_uuid(), -- uses UUID as primary key
    full_name varchar(50) not null, -- employee's full name
    username varchar(50) not null, -- username / employeeID
    password varchar(255) not null, -- user's password, hashed and salted
    role varchar(8) not null check (role in ('CLK', 'SUP', 'CITY-ADM', 'MAYOR', 'ADM')), -- role
    email varchar(50), -- email
    phone varchar(50), -- contact number
    branch varchar(50), -- branch id where they report
    area_id INTEGER references Areas(id)
);


CREATE TABLE Roads (
    id SERIAL primary key, -- road id
    road_name varchar(100) not null, -- name of the road
    locality_type varchar(50) not null, -- locality type
    area_id INTEGER references Areas(id), -- area code
    additional_info TEXT -- any additional information
);

CREATE TABLE Complaints (
    id UUID primary key default gen_random_uuid(), -- uses UUID as primary key
    complaint_date DATE not null, -- date of registration
    road_id INTEGER references Roads(id), -- road id
    repair_type varchar(50) not null, -- type of repair needed
    description TEXT not null, -- description of the complaint
    resident_name varchar(50), -- name of resident
    resident_contact varchar(50), -- resident's contact information
    status varchar(50) not null, -- status of complaint
    severity integer check (severity in (1,2,3,4)), -- severity assigned by supervisor
    priority integer, -- priority assigned by system
    supervisor_id UUID references Users(id) -- supervisor assigned to oversee
);

CREATE TABLE Resources (
    id UUID primary key default gen_random_uuid(), -- uses UUID as primary key
    name varchar(100) not null, -- name of the person or machine(machine id)
    count integer default 0, -- current count of resource of this type
    type varchar(50) not null check (type in ('Personnel', 'Machine')), -- type of resource
    cost double precision default 0.0 -- cost of using this resource
);

CREATE TABLE Materials (
    id UUID primary key default gen_random_uuid(), -- uses UUID as primary key
    type varchar(100) not null, -- type of material
    description TEXT, -- description
    inventory integer default 0, -- current inventory of raw material
    cost double precision default 0.0 -- cost per unit of raw material
);

CREATE TABLE Schedules (
    id UUID primary key default gen_random_uuid(), -- uses UUID as primary key
    complaint_id UUID references Complaints(id),
    scheduled_date DATE NOT NULL,
    start_date DATE NOT NULL,
    deadline DATE NOT NULL,
    end_date DATE NOT NULL,
    status varchar(7) check (status in ('New', 'Ready', 'Doing', 'Blocked', 'Done'))
);

CREATE TABLE Estimate (
    estimate_id UUID primary key default gen_random_uuid(),
    complaint_id UUID references Complaints(id),
    resource_id UUID references Resources(id),
    material_id UUID references Materials(id),
    quantity integer not null default 0,
    constraint chk_estimate CHECK (
        (resource_id is not null and material_id is null) or (resource_id is null and material_id is not null)
    ),
    UNIQUE (complaint_id, resource_id, material_id)
);

/*------------------------------------------------- SYSTEM LOGIC HERE ----------------------------------------------------------*/
CREATE TABLE Access_Logs (
    log_id serial primary key,
    user_id UUID references Users(id), -- user whose access is being tracked
    login_time TIMESTAMP not null, -- login time
    logout_time TIMESTAMP not null -- logout time
);