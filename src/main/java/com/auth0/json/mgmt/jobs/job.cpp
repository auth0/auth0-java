#include <string>

namespace com {
	namespace auth0	{
		namespace json	{
			namespace mgmt	{
				namespace jobs	{
           using namespace com::fasterxml::jackson::annotation;

					/*Class that represents an Auth0 Job object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.*/
					class Job {
					private:
						std::wstring status;
						std::wstring type;
						Date createdAt;
						std::wstring id;
						Job(const std::wstring &status, const std::wstring &type, const std::wstring &id);

					public:
						virtual std::wstring getStatus();
						virtual std::wstring getType();
						virtual Date getCreatedAt();
						virtual std::wstring getId();
					};}}}}}

namespace com {
	namespace auth0 {
		namespace json	{
			namespace mgmt	{
				namespace jobs	{
					using namespace com::fasterxml::jackson::annotation;

					Job::Job(const std::wstring &status, const std::wstring &type, const std::wstring &id)  	{
						this->status = status;
						this->type = type;
						this->id = id;
					}
					std::wstring Job::getStatus()		{
						return status;
					}
					std::wstring Job::getType()			{
						return type;
					}
					Date Job::getCreatedAt()				{
						return createdAt;
					}
					std::wstring Job::getId()				{
						return id;
					}}}}}}
