package ir.jimsa.datagame.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    FILE_BAD_FORMAT("File with provided format is not valid"),
    COULD_NOT_DELETE_RECORD("Could not delete record");

    @Setter
    @Getter
    private String errorMessage;

}
