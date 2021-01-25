<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="forum">
    <jsp:body>
    <h2>
        <c:if test="${forum['new']}">New </c:if> Forum
    </h2>
    <form:form modelAttribute="forum" class="form-horizontal" id="add-forum-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                
                    
                        <button class="btn btn-default" type="submit">Create Forum</button>
                    
                    
               
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>