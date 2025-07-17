---
title: "Day 32: AWS Cloudfront"
description: "A summary of my 32th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 32
  - Challenge
---

### Table of contents :
- [How to control access to your content based on country ?](#how-to-control-access-to-your-content-based-on-country-)


### How to control access to your content based on country ?
- To control access to your content based on the user's country in AWS CloudFront, you use the Geographic Restrictions feature. This allows you to either allowlist (whitelist) specific countries from which users can access your content or blocklist (blacklist) countries from which users are denied access.


- When a user makes a request, CloudFront determines their geographic location based on their IP address and then applies the rules you've configured. If access is denied, CloudFront returns an HTTP 403 (Forbidden) status code.

- **How CloudFront Geographic Restrictions Work**
CloudFront uses a geolocation database (MaxMind GeoIP) to determine the country of origin for each viewer request. Based on your configuration:

  - **Allowlist**: Only users from the specified countries can access your content. All other countries are blocked. This is generally recommended for stricter control, as you explicitly define who can access.

  - **Blocklist**: Users from the specified countries are denied access. Users from all other countries are allowed by default. This is useful if you only need to block a few specific regions.

- **Steps to Set Up Geographic Restrictions in CloudFront**
  - **Sign in to the AWS Management Console**: Go to https://aws.amazon.com/console/ and sign in.

  - **Navigate to CloudFront**: Search for "CloudFront" in the search bar and select the service.

  - **Select Your Distribution**: Click on the ID of the CloudFront distribution you want to configure.

  - **Go to the "Restrictions" Tab**: In the distribution details, click on the "Restrictions" tab.

  - **Edit Geographic Restrictions**: Click the "Edit" button in the "Geographic Restrictions" section.

  - **Configure Restriction Type**:

    - **Restriction Type**:

      - Choose "Allow List" if you want to explicitly allow access only from certain countries.

      - Choose "Block List" if you want to explicitly block access from certain countries, allowing all others.

  - **Select Countries**:

    - In the "Countries" dropdown list, select the countries you want to allow or block based on your chosen Restriction Type. You can select multiple countries.

    - As you select them, they will appear in a list below the dropdown.

  - **Save Changes**: Click "Save changes".

- **Example Scenario**: Allowing Access Only from the United States and Canada
  - **Restriction Type**: Select Allow List.

  - **Countries**: Select United States and Canada.

  - Save Changes.

- Now, only users originating from the United States or Canada will be able to access content through this CloudFront distribution. Requests from any other country will receive a 403 Forbidden error.

- **Example Scenario**: Blocking Access from Russia and China
  - **Restriction Type**: Select Block List.

  - **Countries**: Select Russian Federation and China.

  - Save Changes.

- In this case, users from Russia and China will be blocked, while users from all other countries will be allowed.

- **Important Considerations**:
  - **Propagation Time**: After saving changes, your CloudFront distribution will go into a "Deploying" state. It can take several minutes (typically 5-15 minutes) for the changes to propagate to all CloudFront edge locations globally. During this time, some users might still experience the old behavior.

  - **IP Address Accuracy**: CloudFront relies on geolocation databases, which are generally accurate but not infallible. VPNs, proxy services, and mobile network routing can sometimes mask or misrepresent a user's true geographic location.

  - **Custom Error Pages**: When CloudFront blocks a request due to geographic restrictions, it returns a 403 Forbidden error. You can configure a custom error page for the 403 status code to provide a more user-friendly message explaining why access was denied.

  - **Cost**: There is no additional charge for configuring CloudFront geographic restrictions.

  - **Granularity**: CloudFront's built-in geo-restriction feature works at the country level. If you need more granular control (e.g., by state, city, or even specific IP ranges within a country), you might need to use AWS WAF (Web Application Firewall) with geographic match rules, or implement custom logic using Lambda@Edge by inspecting the CloudFront-Viewer-Country header.


  - **Behavior-Specific Restrictions**: Geographic restrictions apply at the distribution level. If you need different geo-restrictions for different parts of your content (e.g., /us-only/* vs. /global/*), you would need to set up separate CloudFront distributions or use more advanced WAF or Lambda@Edge logic.

