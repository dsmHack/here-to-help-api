insert into organization (organization_guid,name,description,phone_number,email,website_url,facebook_url,twitter_url,instagram_url,address1,city,state,zip_code)
    VALUES ('c849343c-2f0f-11e8-b467-0ed5f89f718b','Amanda the Panda!','Our Mission is to provide innovative grief support services that promote hope and healing to individuals, children, and families.',
    '5152234847','erica@amandathepanda.org','https://amandathepanda.org','https://www.facebook.com/AmandaThePandaCares','https://twitter.com/AmandaPandaDSM','https://www.youtube.com/channel/UCLoZRRCIWIV7fEvGIeYH1yA',
    '1821 Grand Ave','West Des Moines','IA','50265');
insert into organization (organization_guid,name,description,phone_number,email,website_url,facebook_url,twitter_url,instagram_url,address1,city,state,zip_code)
VALUES ('c84936e4-2f0f-11e8-b467-0ed5f89f718b','Community Foundation Of Des Moines','The Community Foundation of Greater Des Moines is a donor-driven public foundation whose purpose is to improve the quality of life in Greater Des Moines through philanthropy.',
        '5152234847','erica@amandathepanda.org','https://www.desmoinesfoundation.org/','https://www.facebook.com/AmandaThePandaCares','https://twitter.com/AmandaPandaDSM','https://www.youtube.com/channel/UCLoZRRCIWIV7fEvGIeYH1yA',
        '1915 Gra Ave','Des Moines','IA','50309');

insert into project (project_guid,organization_guid,name,description,start_date,end_date) values ('c8493824-2f0f-11e8-b467-0ed5f89f718b','c849343c-2f0f-11e8-b467-0ed5f89f718b','Planting Bamboo','We are planting lots of Bamboo',current_timestamp, null);
insert into project (project_guid,organization_guid,name,description,start_date,end_date) values ('c8493950-2f0f-11e8-b467-0ed5f89f718b','c849343c-2f0f-11e8-b467-0ed5f89f718b','Big Hug','We are giving lots of Hugs',current_timestamp, null);

insert into user (user_guid,first_name,last_name,email,role) values ('c8493c34-2f0f-11e8-b467-0ed5f89f718b','Amanda','Panda','apanda@gmail.com','manager');
insert into user (user_guid,first_name,last_name,email,role) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b','Mike','Panda','mpanda@gmail.com','volunteer');

insert into user_organization (user_guid,organization_guid) values ('c8493c34-2f0f-11e8-b467-0ed5f89f718b','c849343c-2f0f-11e8-b467-0ed5f89f718b');
insert into user_organization (user_guid,organization_guid) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b','c849343c-2f0f-11e8-b467-0ed5f89f718b');

insert into check_in (user_guid,project_guid,time_in,time_out) values ('c8493c34-2f0f-11e8-b467-0ed5f89f718b', 'c8493824-2f0f-11e8-b467-0ed5f89f718b','2017-01-01 09:00:00', '2017-01-01 17:00:00');
insert into check_in (user_guid,project_guid,time_in,time_out) values ('c8493c34-2f0f-11e8-b467-0ed5f89f718b', 'c8493950-2f0f-11e8-b467-0ed5f89f718b','2017-01-01 09:00:00', '2017-01-01 15:00:00');

insert into check_in (user_guid,project_guid,time_in,time_out) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b', 'c8493824-2f0f-11e8-b467-0ed5f89f718b','2017-01-01 09:00:00', '2017-01-01 17:00:00');
insert into check_in (user_guid,project_guid,time_in,time_out) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b', 'c8493824-2f0f-11e8-b467-0ed5f89f718b','2017-01-02 09:00:00', '2017-01-02 15:00:00');
insert into check_in (user_guid,project_guid,time_in,time_out) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b', 'c8493824-2f0f-11e8-b467-0ed5f89f718b','2017-01-03 12:00:00', '2017-01-03 17:00:00');

