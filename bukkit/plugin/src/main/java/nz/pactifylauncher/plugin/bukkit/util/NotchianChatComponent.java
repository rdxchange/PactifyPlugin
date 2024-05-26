package nz.pactifylauncher.plugin.bukkit.util;

import java.util.ArrayList;
import java.util.List;

public class NotchianChatComponent {
    private String text;
    private List<NotchianChatComponent> extra;

    public NotchianChatComponent(String text) {
        this.text = text;
        this.extra = new ArrayList<>();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void addExtra(NotchianChatComponent component) {
        extra.add(component);
    }

    public List<NotchianChatComponent> getExtra() {
        return extra;
    }

    public void clearExtra() {
        extra.clear();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"text\":\"").append(text).append("\"");
        if (!extra.isEmpty()) {
            builder.append(",\"extra\":[");
            for (int i = 0; i < extra.size(); i++) {
                if (i != 0) builder.append(",");
                builder.append(extra.get(i));
            }
            builder.append("]");
        }
        builder.append("}");
        return builder.toString();
    }
}