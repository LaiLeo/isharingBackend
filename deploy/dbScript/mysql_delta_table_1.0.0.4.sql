drop view if exists vw_userregisteredevent;
CREATE VIEW vw_userregisteredevent AS
SELECT CONCAT(cu.user_id, cu.registered_event_id) ID, cu.user_id UserId, cu.registered_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate, 
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath, 
1 IsRegister, CASE WHEN cu2.id IS NOT NULL THEN 1 ELSE 0 END IsFocus 
FROM core_userregisteredevent cu 
LEFT JOIN vw_event ve ON cu.registered_event_id = ve.id 
LEFT JOIN core_userfocusedevent cu2 ON cu.user_id = cu2.user_id AND cu.registered_event_id = cu2.focused_event_id 
ORDER BY UserId, EventID;

update db_version set tableVersion = "1.0.0.4", tableDate = now() where id = 1;