<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="messages">
    <h2>
        <c:if test="${message['new']}">New </c:if> Message
    </h2>
    <form:form modelAttribute="message" class="form-horizontal" id="add-team-form">
        <div class="form-group has-feedback">
            <petclinic:selectField label="Attachment Type" name="AttachmentType" names="${AttachmentType}" size="${AttachmentType.size()}"/>
            <petclinic:inputField label="Url" name="url"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${message['new']}">
                        <button class="btn btn-default" type="submit">Send message</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update message</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>