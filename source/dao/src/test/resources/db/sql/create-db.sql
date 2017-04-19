CREATE TABLE Site (
        Id INT NOT NULL GENERATED ALWAYS AS IDENTITY ,
        ShortName VARCHAR(50) NOT NULL,
        StreetAddress VARCHAR(250) NOT NULL,
        City VARCHAR(100) NOT NULL,
        State VARCHAR(2) NOT NULL,
        Zip VARCHAR(10) NOT NULL,
        PhoneNumber VARCHAR(12) NOT NULL,
        PRIMARY KEY (Id)
);

CREATE TABLE Account (
        UserName VARCHAR(25) NOT NULL,
        Password VARCHAR(50) NOT NULL,
        PRIMARY KEY (UserName)
);


CREATE TABLE "User"(
        UserName VARCHAR(25) NOT NULL,
        Email VARCHAR(250) NOT NULL,
        FirstName VARCHAR(50) NOT NULL,
        LastName VARCHAR(50) NOT NULL,
        Role VARCHAR(25) NOT NULL,
        SiteId INT NOT NULL,
        PRIMARY KEY (Email),
        CONSTRAINT User_username_Account FOREIGN KEY (UserName) REFERENCES Account (UserName),
        CONSTRAINT User_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id)
);

-- Create Tables for ENUMs
CREATE TABLE ItemCategoryType(
                Category VARCHAR(50) NOT NULL,
                CategoryInt INT NOT NULL,
                PRIMARY KEY (Category)
);

CREATE TABLE ItemStorageType(
                StorageType VARCHAR(50) NOT NULL,
                StorageTypeInt INT NOT NULL,
                PRIMARY KEY (StorageType)
);

CREATE TABLE FoodItemCategory(
                Category VARCHAR(50) NOT NULL,
                CategoryInt INT NOT NULL,
                PRIMARY KEY (Category)
);      

CREATE TABLE SupplyItemCategory(
                Category VARCHAR(50) NOT NULL,
                CategoryInt INT NOT NULL,
                PRIMARY KEY (Category)
);      

CREATE TABLE RequestStatus(
                StatusDesc VARCHAR (50) NOT NULL,
                StatusInt INT NOT NULL,
                PRIMARY KEY (StatusDesc)
);

CREATE TABLE ClientServiceCategory(
                Category VARCHAR(50) NOT NULL,
                CategoryInt INT NOT NULL,
                PRIMARY KEY (Category)
);

CREATE TABLE FoodBank(
        ServiceId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
        SiteId INT NOT NULL,
        PRIMARY KEY (ServiceId),
        UNIQUE(SiteId),
        CONSTRAINT FoodBank_siteid_SiteId FOREIGN KEY (SiteId) REFERENCES Site (Id) ON DELETE CASCADE
);

CREATE TABLE Item(
        ItemId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
        SiteId INT NOT NULL,
        Category VARCHAR(50) NOT NULL,
        ExpireDate DATE NOT NULL,
        NumberUnits INT NOT NULL,
        Name VARCHAR(250) NOT NULL,
        StorageType VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId), 
        CONSTRAINT Item_serviceid_FoodBank FOREIGN KEY (SiteId) REFERENCES FoodBank (SiteId) ON DELETE CASCADE,
        CONSTRAINT Item_category_ItemCategoryType FOREIGN KEY (Category) REFERENCES ItemCategoryType (Category),
        CONSTRAINT Item_storagetype_ItemStorageType FOREIGN KEY (StorageType) REFERENCES ItemStorageType (StorageType)
);

CREATE TABLE FoodItem(
        ItemId INT NOT NULL,
        FoodCategory VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId),
        CONSTRAINT FoodItem_itemid_Item FOREIGN KEY (ItemId) REFERENCES Item (ItemId) ON DELETE CASCADE,
        CONSTRAINT FoodItem_foodcategory_FoodItemCategory FOREIGN KEY (FoodCategory) REFERENCES FoodItemCategory (Category)
);

CREATE TABLE SupplyItem(
        ItemId INT NOT NULL,
        SupplyCategory VARCHAR(50) NOT NULL,
        PRIMARY KEY (ItemId),
        CONSTRAINT SupplyItem_itemid_Item FOREIGN KEY (ItemId) REFERENCES Item (ItemId) ON DELETE CASCADE,
        CONSTRAINT SupplyItem_supplycategory_SupplyItemCategory FOREIGN KEY (SupplyCategory) REFERENCES SupplyItemCategory (Category)
);

CREATE TABLE RequestedItem(
        ItemId INT NOT NULL,
        RequesteeSiteId INT NOT NULL,
        ReqDateTime TIMESTAMP NOT NULL,
        UserId VARCHAR(250) NOT NULL,
        Status VARCHAR (50) NOT NULL,
        NumRequested INT NOT NULL DEFAULT 1,
        NumFilled INT NOT NULL DEFAULT 0,
        PRIMARY KEY (RequesteeSiteId, ReqDateTime),
        CONSTRAINT RequestedItem_itemid_Item FOREIGN KEY (ItemId)REFERENCES Item(ItemId),
        CONSTRAINT RequestedItem_userid_User FOREIGN KEY (UserId) REFERENCES "User"(Email),
        CONSTRAINT RequestedItem_requesteesiteid_Site FOREIGN KEY (RequesteeSiteId) REFERENCES Site(Id),
        CONSTRAINT RequestedItem_status_RequestStatus FOREIGN KEY (Status) REFERENCES RequestStatus(StatusDesc)
);

CREATE TABLE ClientService(
        SiteId INT NOT NULL,
        ServiceId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
        ServiceCategory VARCHAR(50) NOT NULL,
        Description VARCHAR(500),
        HoursOperation VARCHAR(500) NOT NULL,
        ConditionUse VARCHAR(500) NOT NULL,
        PRIMARY KEY (ServiceId),
        CONSTRAINT ClientService_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id),
        CONSTRAINT ClientService_servicecategory_ClientServiceCategory FOREIGN KEY (ServiceCategory) REFERENCES ClientServiceCategory (Category),
        CONSTRAINT ClientService_unq_SiteId_ServiceCategory UNIQUE (SiteId, ServiceCategory)
);


CREATE TABLE SoupKitchen(
        SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        AvailableSeats INT NOT NULL DEFAULT 0,
        PRIMARY KEY (ServiceId),
        CONSTRAINT SoupKitchen_serviceid_ClientService FOREIGN KEY (ServiceId) REFERENCES ClientService (ServiceId) ON DELETE CASCADE,
        CONSTRAINT SoupKitchen_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id)         
);

CREATE TABLE Shelter(
        SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        FamilyRoomCount INT NOT NULL DEFAULT 0,
        MaleBunkCount INT NOT NULL DEFAULT 0,
        FemaleBunkCount INT NOT NULL DEFAULT 0,
        MixedBunkCount INT NOT NULL DEFAULT 0,
        TotalBunkCount INT NOT NULL DEFAULT 0,
        PRIMARY KEY (ServiceId),
        CONSTRAINT Shelter_serviceid_ClientService FOREIGN KEY (ServiceId) REFERENCES ClientService (ServiceId) ON DELETE CASCADE,
        CONSTRAINT Shelter_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id)
        
);

CREATE TABLE FoodPantry(
        SiteId INT NOT NULL,
        ServiceId INT NOT NULL,
        PRIMARY KEY (ServiceId),
        CONSTRAINT FoodPantry_serviceid_ClientService FOREIGN KEY (ServiceId) REFERENCES ClientService (ServiceId) ON DELETE CASCADE,
        CONSTRAINT FoodPantry_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id) ON DELETE CASCADE
                                
);

CREATE TABLE Client(
        Identifier INT NOT NULL GENERATED ALWAYS AS IDENTITY,
        Description VARCHAR(500) NOT NULL,
        Name VARCHAR(100) NOT NULL,
        Phone VARCHAR(12),
        PRIMARY KEY (Identifier)
);

CREATE TABLE WaitList(
        ClientId INT NOT NULL,
        SiteId INT NOT NULL,
        Position INT NOT NULL,
        PRIMARY KEY (SiteId,ClientId),
        CONSTRAINT WaitList_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id),
        CONSTRAINT WaitList_clientid_Client FOREIGN KEY (ClientId) REFERENCES Client(Identifier)
);

CREATE TABLE LogEntry(
        ClientId INT NOT NULL,
        SiteId INT NOT NULL,
        DateTime TIMESTAMP NOT NULL,
        Notes VARCHAR(500),
        DescOfService VARCHAR(500),
        PRIMARY KEY (SiteId,DateTime),
        CONSTRAINT LogEntry_siteid_Site FOREIGN KEY (SiteId) REFERENCES Site (Id),
        CONSTRAINT LogEntry_clientid_Client FOREIGN KEY (ClientId) REFERENCES Client(Identifier)
);


-- Populate ENUM Tables
INSERT INTO ItemCategoryType(Category, CategoryInt)
VALUES ('Food', 0);
INSERT INTO ItemCategoryType(Category, CategoryInt)
VALUES ('Supply', 1);

INSERT INTO ItemStorageType(StorageType, StorageTypeInt)
VALUES ('Dry Good', 0);
INSERT INTO ItemStorageType(StorageType, StorageTypeInt)
VALUES ('Refrigerated', 1);
INSERT INTO ItemStorageType(StorageType, StorageTypeInt)
VALUES ('Frozen', 2);

INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Vegetables', 0);
INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Nuts/Grains/Beans', 1); 
INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Meat/Seafood', 2);
INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Dairy/Eggs', 3);
INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Sauce/Condiment/Seasoning', 4);
INSERT INTO FoodItemCategory(Category, CategoryInt)
VALUES('Juice/Drink', 5);

INSERT INTO SupplyItemCategory(Category, CategoryInt)
VALUES('Personal Hygiene', 0);
INSERT INTO SupplyItemCategory(Category, CategoryInt)
VALUES('Clothing', 1);  
INSERT INTO SupplyItemCategory(Category, CategoryInt)
VALUES('Shelter', 2);   
INSERT INTO SupplyItemCategory(Category, CategoryInt)
VALUES('Other', 3);             

INSERT INTO RequestStatus(StatusDesc, StatusInt)
VALUES ('Pending',0);
INSERT INTO RequestStatus(StatusDesc, StatusInt)
VALUES ('Closed', 1);

INSERT INTO ClientServiceCategory(Category, CategoryInt)
VALUES ('FoodPantry', 0);
INSERT INTO ClientServiceCategory(Category, CategoryInt)
VALUES ('Shelter', 1);
INSERT INTO ClientServiceCategory(Category, CategoryInt)
VALUES ('SoupKitchen', 2);