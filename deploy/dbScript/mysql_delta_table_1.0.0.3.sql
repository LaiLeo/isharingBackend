drop view if exists vw_event;
CREATE VIEW vw_event AS
select e.id,e.owner_NPO_id,e.image_link_1,e.image_link_2,e.image_link_3,e.image_link_4,e.image_link_5,e.uid,e.tags,e.pub_date
     ,e.happen_date,e.close_date,e.register_deadline_date,e.subject,e.description,e.event_hour,e.focus_num,e.address_city
     ,e.address,e.insurance,e.insurance_description,e.volunteer_training,e.volunteer_training_description,e.lat,e.lng
     ,e.required_volunteer_number,e.current_volunteer_number,e.required_group,e.skills_description,e.user_account_id
     ,e.thumb_path,e.reply_num,e.rating_user_num,e.total_rating_score,e.is_volunteer_event,e.is_urgent,e.leave_uid
     ,e.require_signout,e.is_short,e.volunteer_type,e.donation_serial,e.donation_start_date,e.donation_end_date
     ,e.service_type,e.foreign_third_party_id,n.name as npoName,
    case when e.required_volunteer_number - current_volunteer_number = 0 then '1' else '0' end as isFull,
    n.is_enterprise
from core_event e
left join core_npo n on e.owner_npo_id = n.id
order by e.is_Urgent desc;

drop view if exists vw_event_reply;
CREATE VIEW vw_event_reply AS
select r.id, r.reply_time, r.message, r.image, r.thumb_path, r.event_id,
       u.id as userId, u.name as userName, u.photo as userPhoto, u.icon as userIcon
from core_reply r
left join vw_users u on r.user_account_id = u.id
where u.is_active = 1
order by r.reply_time desc;


update db_version set tableVersion = "1.0.0.3", tableDate = now() where id = 1;