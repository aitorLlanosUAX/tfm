terraform{
    required_providers{
        aws = {
            source = "hasicorp/aws"
            version"3.58.0
        }
    }
}

provider "aws"{
    region = "eu-west-3"
    aws_key = 
}
