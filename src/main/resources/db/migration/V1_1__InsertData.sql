insert into organization (org_guid,name,description,phone,email,web_url,facebook,twitter,instagram,address1,city,state,zip)
    VALUES ('c849343c-2f0f-11e8-b467-0ed5f89f718b','Amanda the Panda!','Our Mission is to provide innovative grief support services that promote hope and healing to individuals, children, and families.',
    '5152234847','erica@amandathepanda.org','https://amandathepanda.org','https://www.facebook.com/AmandaThePandaCares','https://twitter.com/AmandaPandaDSM','https://www.youtube.com/channel/UCLoZRRCIWIV7fEvGIeYH1yA',
    '1821 Grand Ave','West Des Moines','IA','50265');

insert into organization (org_guid,name,description,phone,email,web_url,facebook,twitter,instagram,address1,city,state,zip)
VALUES ('c84936e4-2f0f-11e8-b467-0ed5f89f718b','Community Foundation Of Des Moines','The Community Foundation of Greater Des Moines is a donor-driven public foundation whose purpose is to improve the quality of life in Greater Des Moines through philanthropy.',
        '5152234847','erica@amandathepanda.org','https://www.desmoinesfoundation.org/','https://www.facebook.com/AmandaThePandaCares','https://twitter.com/AmandaPandaDSM','https://www.youtube.com/channel/UCLoZRRCIWIV7fEvGIeYH1yA',
        '1915 Gra Ave','Des Moines','IA','50309');

insert into project (proj_guid,proj_id,org_guid,name,description,start_dt,end_dt) values ('c8493824-2f0f-11e8-b467-0ed5f89f718b','1234567',
        'c849343c-2f0f-11e8-b467-0ed5f89f718b','Planting Bamboo','We are planting lots of Bamboo',current_timestamp, null);

insert into project (proj_guid,proj_id,org_guid,name,description,start_dt,end_dt) values ('c8493950-2f0f-11e8-b467-0ed5f89f718b','1234568',
                                                                                          'c849343c-2f0f-11e8-b467-0ed5f89f718b','Big Hug','We are giving lots of Hugs',current_timestamp, null);

insert into user (user_guid,user_id,first_name,last_name,email,role) values ('c8493c34-2f0f-11e8-b467-0ed5f89f718b','amandap','Amanda','Panda','apanda@gmail.com','manager');
insert into user (user_guid,user_id,first_name,last_name,email,role) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b','mikep','Mike','Panda','mpanda@gmail.com','volunteer');

insert into user_org (user_guid,org_guid) values ('c84940da-2f0f-11e8-b467-0ed5f89f718b','c849343c-2f0f-11e8-b467-0ed5f89f718b');
