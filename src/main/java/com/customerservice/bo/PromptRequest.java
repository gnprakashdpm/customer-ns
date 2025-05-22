package com.customerservice.bo;

public class PromptRequest {

    private String prompt;
    private String title;
    private String description;
    private String storeCode;
    private SessionMetadata sessionMetadata;

    // Default constructor
    public PromptRequest() {}

    // Parameterized constructor
    public PromptRequest(String prompt, String title, String description, String storeCode, SessionMetadata sessionMetadata) {
        this.prompt = prompt;
        this.title = title;
        this.description = description;
        this.storeCode = storeCode;
        this.sessionMetadata = sessionMetadata;
    }

    // Getters and Setters
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public SessionMetadata getSessionMetadata() {
		return sessionMetadata;
	}

	public void setSessionMetadata(SessionMetadata sessionMetadata) {
		this.sessionMetadata = sessionMetadata;
	}

	// toString method
    @Override
    public String toString() {
        return "ProductInfo{" +
                "prompt='" + prompt + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", sessionMetadata='" + sessionMetadata +
                '}';
    }
}
