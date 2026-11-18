package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoticeCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 16, message = "Too long content")
    private String content;

    public @NotNull @NotEmpty @Size(min = 2, max = 16, message = "Too long content") String getContent() {
        return content;
    }

    public void setContent(@NotNull @NotEmpty @Size(min = 2, max = 16, message = "Too long content") String content) {
        this.content = content;
    }
}
