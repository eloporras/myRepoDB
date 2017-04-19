INSERT INTO Site (ShortName, StreetAddress, City, State, Zip, PhoneNumber) VALUES ('JUnit Testing Site 1', 'North Ave NW', 'Atlanta', 'GA', '30332', '404-894-2000');
INSERT INTO Site (ShortName, StreetAddress, City, State, Zip, PhoneNumber) VALUES ('JUnit Testing Site 2', 'North Ave NW', 'Atlanta', 'GA', '30332', '404-894-2000');
INSERT INTO Site (ShortName, StreetAddress, City, State, Zip, PhoneNumber) VALUES ('JUnit Testing Site 3', 'North Ave NW', 'Atlanta', 'GA', '30332', '404-894-2000');

INSERT INTO ClientService (SiteId, ServiceCategory, Description, HoursOperation, ConditionUse) VALUES (1, 'FoodPantry', 'FoodPantry for site 1', '9a-5p M-F', 'Picture ID/driver license, social security card, birth certificate, proof of residence or lease, proof of income');
INSERT INTO FoodPantry (SiteId, ServiceId) VALUES (1, 1);

INSERT INTO ClientService (SiteId, ServiceCategory, Description, HoursOperation, ConditionUse) VALUES (1, 'Shelter', 'Shelter for site 1', '9a-5p M-F', 'Picture ID/driver license, social security card, birth certificate, proof of residence or lease, proof of income');
INSERT INTO Shelter (SiteId, ServiceId, FamilyRoomCount, MaleBunkCount, FemaleBunkCount, MixedBunkCount, TotalBunkCount) VALUES (1, 2, 2, 3, 4, 5, 12);

INSERT INTO ClientService (SiteId, ServiceCategory, Description, HoursOperation, ConditionUse) VALUES (1, 'SoupKitchen', 'SoupKitchen for site 1', '9a-5p M-F', 'Picture ID/driver license, social security card, birth certificate, proof of residence or lease, proof of income');
INSERT INTO SoupKitchen (SiteId, ServiceId, AvailableSeats) VALUES (1, 3, 3);

INSERT INTO FoodBank (SiteId) VALUES (1);
INSERT INTO FoodBank (SiteId) VALUES (2);

INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 2, 'Veggy', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 3, 'Veggy', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 4, 'Veggy', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 5, 'Veggy', 'Refrigerated');

INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 2, 'Meats', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 2, 'Meats', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 2, 'Meats', 'Refrigerated');
INSERT INTO Item (SiteId, Category, ExpireDate, NumberUnits, Name, StorageType) VALUES (2, 'Food', '2018-04-04', 2, 'Meats', 'Refrigerated');

INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (1, 'Vegetables');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (2, 'Vegetables');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (3, 'Vegetables');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (4, 'Vegetables');

INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (5, 'Meat/Seafood');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (6, 'Meat/Seafood');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (7, 'Meat/Seafood');
INSERT INTO FoodItem (ItemId, FoodCategory) VALUES (8, 'Meat/Seafood');

INSERT INTO Client (Description, Name, Phone) VALUES ('Junit Test Client #1', 'Junit, Test1', '555-867-5309');
INSERT INTO Client (Description, Name, Phone) VALUES ('Junit Test Client #2', 'Junit, Test2', '555-867-5309');

INSERT INTO WaitList (ClientId, SiteId, Position) VALUES (1, 1, 1);
INSERT INTO WaitList (ClientId, SiteId, Position) VALUES (2, 1, 2);
