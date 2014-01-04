package gameserver.core;

/**
 *
 * @author caoxin
 */
public class Mail {

    private int receiver;
    private int sender;
    private String title;
    private String content;
    private String createTime;
    private MailStatus mailStatus;
    private ItemPair[] attachment = new ItemPair[2];

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MailStatus getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(MailStatus mailStatus) {
        this.mailStatus = mailStatus;
    }

    public ItemPair[] getAttachment() {
        return attachment;
    }

    public void setAttachment(ItemPair[] attachment) {
        this.attachment = attachment;
    }
}