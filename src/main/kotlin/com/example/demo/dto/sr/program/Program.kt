package com.example.demo.dto.sr.program


import com.fasterxml.jackson.annotation.JsonProperty

data class Program(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("payoff")
    val payoff: String?,
    @JsonProperty("broadcastinfo")
    val broadcastInfo: String?,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("phone")
    val phone: String,
    @JsonProperty("programurl")
    val programUrl: String,
    @JsonProperty("programslug")
    val programSlug: String?,
    @JsonProperty("programimage")
    val programImage: String,
    @JsonProperty("programimagetemplate")
    val programImageTemplate: String,
    @JsonProperty("programimagewide")
    val programImageWide: String,
    @JsonProperty("programimagetemplatewide")
    val programImageTemplateWide: String,
    @JsonProperty("socialimage")
    val socialImage: String,
    @JsonProperty("socialimagetemplate")
    val socialImageTemplate: String,
    @JsonProperty("socialmediaplatforms")
    val socialMediaPlatforms: List<SocialMediaPlatform>,
    @JsonProperty("channel")
    val channel: Channel,
    @JsonProperty("archived")
    val archived: String,
    @JsonProperty("hasondemand")
    val hasOnDemand: String,
    @JsonProperty("haspod")
    val hasPod: String,
    @JsonProperty("responsibleeditor")
    val responsibleEditor: String?,
    @JsonProperty("programcategory")
    val programCategory: ProgramCategory? // Add this field
)